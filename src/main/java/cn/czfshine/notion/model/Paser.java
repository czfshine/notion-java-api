package cn.czfshine.notion.model;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
public class Paser {

    /* **** 一些内部类，与json一一对应，用来Gson解析用 ****** */

    //Gson 在解析的时候，多一个域或者少一个域都不会出错，
    // 所以可以复用多个相似的POJO，懒得写继承了
    //但是注意类型要正确，会抛出一个免检异常的
    private static class Info{
        String role;
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
            List<HashMap<String,Object>> permissions;


            String name;
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

    private static class ResponeJson {
        class RecordMap{
            HashMap<String,Info> block;
            HashMap<String,Info> space;
            HashMap<String,Info> notion_user;
            HashMap<String, Info> user_root;
            HashMap<String, Info> user_settings;
            HashMap<String, Info> space_view;
        }
        RecordMap recordMap;
        HashMap<String,LinkedTreeMap> cursor;
    }

    private static Gson gson = new Gson();
    /**
     * 分析页面数据
     **/
    public static  void paserPageChunk(String json ){


        //todo 解析异常
        ResponeJson pageChunk = gson.fromJson(json, ResponeJson.class);

        //users's info
        HashMap<String, Info> notion_user = pageChunk.recordMap.notion_user;

        Set<String> set = notion_user.keySet();
        for (String uuid:set
             ) {

            Info user = notion_user.get(uuid);
            log.debug("get user - role:{} name :{} email :{}",
                    user.role,user.value.family_name+" "+user.value.given_name,user.value.email);
        }

        //workspaces

        HashMap<String, Info> workspaces = pageChunk.recordMap.space;
        Set<String> strings = workspaces.keySet();
        for (String uuid: strings
             ) {
            log.debug("workspace name:{}", workspaces.get(uuid).value);
        }
        //blocks

        HashMap<String, Info> blocks = pageChunk.recordMap.block;

        for (String uuid : blocks.keySet()
             ) {
            Info block = blocks.get(uuid);
            BlockType typeByName = BlockType.getTypeByName(block.value.type);

            if (typeByName.equals(BlockType.TEXT)) {
                parserTextBlock(uuid, block);
            }

            if (typeByName.equals(BlockType.UNKNOWN)) {
                log.warn("block - unknown type:{}", block.value.type);
            }
        }
        //System.out.println(gson.toJson(pageChunk));
    }

    private static String parserTitle(ArrayList title) {

        String res = "";


        for (Object o : title
        ) {
            ArrayList arrayList = (ArrayList) o;

            if (arrayList.size() == 1) {
                res += arrayList.get(0).toString();
            } else {
                //todo
            }
        }

        return res;
    }

    private static void parserTextBlock(String uuid, Info block) {
        //System.out.println(uuid);
        if (block.value.properties != null) {
            //System.out.println(parserTitle((ArrayList) block.value.properties.get("title")));
        }
    }


    public static void parserUserContent(String json) {
        ResponeJson responeJson = gson.fromJson(json, ResponeJson.class);

    }
}
