package cn.czfshine.notion.parser;

import cn.czfshine.notion.model.Block;
import cn.czfshine.notion.model.BlockType;
import cn.czfshine.notion.model.PageBlock;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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


            String name;

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
    public static void parserPageChunk(String json) {

        log.debug("start parser page chunk...");

        //todo 解析异常
        ResponeJson pageChunk = gson.fromJson(json, ResponeJson.class);

        HashMap<String, JsonInfo> notion_user = pageChunk.recordMap.notion_user;
        convertUsers(notion_user);
        HashMap<String, JsonInfo> workspaces = pageChunk.recordMap.space;
        convertWorkspaces(workspaces);
        HashMap<String, JsonInfo> blocks = pageChunk.recordMap.block;
        convertBlocksData(blocks);

        //System.out.println(gson.toJson(pageChunk));

        log.debug("end parser page chunk...");
    }

    private static void convertUsers(HashMap<String, JsonInfo> notion_user) {
        Set<String> set = notion_user.keySet();
        for (String uuid : set
        ) {

            JsonInfo user = notion_user.get(uuid);
            log.debug("get user - role:{} name :{} email :{}",
                    user.role, user.value.family_name + " " + user.value.given_name, user.value.email);
        }
    }

    private static void convertWorkspaces(HashMap<String, JsonInfo> workspaces) {
        Set<String> strings = workspaces.keySet();
        for (String uuid : strings
        ) {
            log.debug("workspace name:{}", workspaces.get(uuid).value);
        }
    }

    private static void convertBlocksData(HashMap<String, JsonInfo> blocks) {
        HashMap<String, Block> allBlocks = new HashMap<>();
        for (String uuid : blocks.keySet()
        ) {
            JsonInfo blockInfo = blocks.get(uuid);
            BlockType typeByName = BlockType.getTypeByName(blockInfo.value.type);
            Block block = null;

            log.debug("get block type:{}", typeByName);
            log.debug("data :{}", blockInfo.value);

            if (typeByName.equals(BlockType.UNKNOWN)) {
                log.warn("unknow type :{}", blockInfo.value.type);
            }

            if (typeByName.equals(BlockType.PAGE)) {
                continue;
            }

            block = BlockFactory.createBlockByType(blockInfo);
            allBlocks.put(uuid, block);
        }
        //page
        for (String uuid : blocks.keySet()
        ) {
            JsonInfo blockInfo = blocks.get(uuid);
            BlockType typeByName = BlockType.getTypeByName(blockInfo.value.type);
            if (typeByName.equals(BlockType.PAGE)) {
                PageBlock pageBlock = BlockFactory.createPageBlock(blockInfo);
                List<String> content = blockInfo.value.content;

                if (content == null) {
                    continue;
                }
                pageBlock.setContentIds(content);
                for (int i = 0; i < content.size(); i++) {
                    //todo
                    Block orDefault = allBlocks.getOrDefault(content.get(i), null);
                    if (orDefault == null) {
                        log.warn("the page {} has sub block {} but not exits!", uuid, content.get(i));
                    } else {
                        pageBlock.addBlockContent(orDefault);
                    }
                }
                log.debug("the page :{}", pageBlock);
            }
        }
    }


    private static void parserTextBlock(String uuid, JsonInfo block) {
        //System.out.println(uuid);
        if (block.value.properties != null) {
            //System.out.println(parserTitle((ArrayList) block.value.properties.get("text")));
        }
    }


    public static void parserUserContent(String json) {
        ResponeJson responeJson = gson.fromJson(json, ResponeJson.class);

    }
}
