package cn.czfshine.notion.parser;

import cn.czfshine.notion.model.User;
import cn.czfshine.notion.model.WorkSpace;
import cn.czfshine.notion.model.block.Block;
import cn.czfshine.notion.model.block.BlockType;
import cn.czfshine.notion.model.block.PageBlock;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**解析Json到对象.
 * 注意：有些内部类和json的key是一一对应的，
 * 为了方便解析，所以命名不规范
 * @author:czfshine
 * @date:2019/6/30 10:16
 */
@Slf4j
public class Parser {

    /* **** 一些内部类，与json一一对应，用来Gson解析用 ****** */

    //Gson 在解析的时候，多一个域或者少一个域都不会出错，
    // 所以可以复用多个相似的POJO，懒得写继承了
    //但是注意类型要正确，会抛出一个免检异常的
    //一些暂时没用的全部用hashmap表示，最后要全部定义成对象

    @Data
    public static class JsonInfo {
        String role;

        @Data
        class Value{

            //User

            String id;
            Integer version;
            String email;
            String given_name;
            String family_name;
            Boolean onboarding_completed;

            //Page/block
            String type;

            //不同类型的block（目前有30多种block）有不同的属性，太多了，就不一一定义
            //到时把这个map丢到工厂函数构建对象即可
            HashMap<String,Object> properties;
            List<String> content;
            List<String> discussions;

            List<HashMap<String,Object>> permissions;

            //workspace
            String name;
            String domain;
            List<String> pages;

            @Data
            class Format {
                String display_source;
                boolean block_full_width;
                int block_height;
                boolean block_page_width;
                boolean block_preserve_scale;
                int block_width;
                String block_color;
                String page_icon;
            }

            Format format;

            //common
            String created_by;
            //todo 时间戳->Date
            Long created_time;
            String last_edited_by;
            Long last_edited_time;
            String parent_id;
            String parent_table;
            Boolean alive;
        }
        Value value;
    }

    @Data
    private static class ResponeJson {
        @Data
        class RecordMap{
            //下面map的key有些是uuid
            HashMap<String, JsonInfo> block;
            HashMap<String, JsonInfo> space;
            HashMap<String, JsonInfo> notion_user;
            HashMap<String, JsonInfo> user_root;
            HashMap<String, JsonInfo> user_settings;
            HashMap<String, JsonInfo> space_view;
        }

        RecordMap recordMap;
        HashMap<String,LinkedTreeMap> cursor;
    }

    private static Gson gson = new Gson();
    /**
     * 分析页面数据
     **/
    public static WorkSpace parserPageChunk(String json) {

        log.debug("start parser page chunk...");

        //todo 解析异常
        ResponeJson pageChunk = gson.fromJson(json, ResponeJson.class);

        HashMap<String, JsonInfo> notion_user = pageChunk.recordMap.notion_user;
        convertUsers(notion_user);

        HashMap<String, JsonInfo> blocks = pageChunk.recordMap.block;
        convertBlocksData(blocks);

        // 先解析页面再解析工作区
        HashMap<String, JsonInfo> workspaces = pageChunk.recordMap.space;
        WorkSpace workSpace = null;
        try {
            workSpace = convertWorkspaces(workspaces);
        } catch (ParserException e) {
            e.printStackTrace();
        }

        //System.out.println(gson.toJson(pageChunk));

        log.debug("end parser page chunk...");
        return workSpace;
    }

    private static HashMap<String, User> allUsers = new HashMap<>();

    private static void convertUsers(HashMap<String, JsonInfo> notion_user) {
        Set<String> set = notion_user.keySet();
        for (String uuid : set
        ) {

            JsonInfo user = notion_user.get(uuid);
            log.debug("get user - role:{} name :{} email :{}",
                    user.role, user.value.family_name + " " + user.value.given_name, user.value.email);
        }
    }

    static class ParserException extends Exception {

    }

    private static WorkSpace convertWorkspaces(HashMap<String, JsonInfo> workspaces) throws ParserException {

        Set<String> strings = workspaces.keySet();
        if (strings.size() == 0) {
            log.error("must had a space info ");
            //无法解决的
            throw new ParserException();
        }
        if (strings.size() > 1) {
            //这个方法是加载页面的时调用的，所以一个页面只属于一个space，理论上不会载入多个space
            log.warn("get too many space,use first item");
        }

        JsonInfo jsonInfo = workspaces.get(strings.iterator().next());
        JsonInfo.Value value = jsonInfo.value;
        WorkSpace workSpace = new WorkSpace();

        //basic info
        workSpace.setDomain(value.domain);
        workSpace.setName(value.name);

        //user info
        value.permissions.forEach((e) -> {
            Object uid = e.get("user_id");
            User user = allUsers.get(uid);
            if (user != null) {
                workSpace.getUsers().add(user);
            } else {
                log.warn("space:{} has user:{} ,but not parser it", value.id, uid);
            }
        });

        List<String> pages = value.pages;
        if (pages != null) {
            //todo  ordered
            workSpace.getPageIds().addAll(pages);
        }
        return workSpace;
    }

    private static void convertBlocksData(HashMap<String, JsonInfo> blocks) {

        //需要引用另外的block的block，得在所有block解析完后进行二次处理
        List<Block> hasContentBlocks = new LinkedList<>();

        BlockPool thePool = BlockPool.getThePool();
        blocks.keySet().forEach((uuid) -> {
            JsonInfo blockInfo = blocks.get(uuid);
            BlockType type = BlockType.getTypeByName(blockInfo.value.type);
            Block block = null;

//            log.debug("get block type:{}", type);
//            log.debug("data :{}", blockInfo.value);

            if (type.equals(BlockType.UNKNOWN)) {
                log.warn("unknow type :{}", blockInfo.value.type);
            }

            block = BlockFactory.createBlockByType(blockInfo);

            thePool.put(block);

            switch (type) {
                case PAGE:
                case COLUMN:
                case FACTORY:
                case COLUMNLIST: {
                    hasContentBlocks.add(block);
                }
                default: {
                    break;
                }
            }
        });

        hasContentBlocks.forEach((block) -> {
            if (block instanceof PageBlock) {
                List<String> contentIds = ((PageBlock) block).getContentIds();
                contentIds.forEach((id) -> {
                    //todo foreach的顺序是否和获取的一样
                    Optional<Block> subblock = thePool.get(id);
                    if (subblock.isPresent()) {
                        ((PageBlock) block).addBlockContent(subblock.get());
                    } else {
                        //todo 根据api的分析，理论上应该会全部获取的，不会出现这种情况，或者可能有分页
                        log.error("the block {} has subblock {},but not got it", block.getId(), id);
                    }
                });
            } else {
                //todo column
            }
        });
    }

    public static void parserUserContent(String json) {
        ResponeJson responeJson = gson.fromJson(json, ResponeJson.class);

    }
}
