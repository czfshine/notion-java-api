package cn.czfshine.notion.model;

import java.util.HashMap;

public enum BlockType {

    //only one text
    TEXT("text"),
    HEADER("header"),
    SUBHEADER("sub_header"),
    SUBSUBHEADER("sub_sub_header"),
    EQUATION("equation"),

    QUOTE("quote"),
    CODE("code"),//?
    CALLOUT("callout"),

    // a link
    EMBED("embed"),
    BOOKMARK("bookmark"),

    // a file
    AUDIO("audio"),
    IMAGE("image"),
    FILE("file"),
    VIDEO("video"),


    //lists
    TOGGLE("toggle"),
    TODO("to_do"),
    NUMBEREDLIST("numbered_list"),
    BULLETEDLIST("bulleted_list"),

    //nothing
    DIVIDER("divider"),


    //what is this ?
    Collectionview("collection_view"),
    PAGE("page"),
    BREADCRUMB("breadcrumb"),
    FACTORY("factory"),
    MAPS("maps"),
    DRIVE("drive"),
    GIST("gist"),
    TABLEOFCONTENTS("table_of_contents"),
    TWEET("tweet"),
    COLLECTIONVIEWPAGE("collection_view_page"),
    UNKNOWN("unknown");

    private static class AllType {
        static HashMap<String, BlockType> allType = new HashMap<String, BlockType>();
    }

    private String name;

    BlockType(String name) {
        AllType.allType.put(name, this);
        this.name = name;
    }

    public static BlockType getTypeByName(String name) {
        return AllType.allType.getOrDefault(name, UNKNOWN);
    }
}
