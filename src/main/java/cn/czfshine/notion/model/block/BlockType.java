package cn.czfshine.notion.model.block;

import cn.czfshine.notion.parser.BlockFactory;
import cn.czfshine.notion.parser.Parser;

import java.util.HashMap;
import java.util.function.Function;

public enum BlockType {

    //only one text
    TEXT("text", BlockFactory::createTextBlock),
    HEADER("header", BlockFactory::createHeaderBlock),
    SUBHEADER("sub_header", BlockFactory::createHeaderBlock),
    // 3级标题
    SUBSUBHEADER("sub_sub_header", BlockFactory::createHeaderBlock),
    //数学公式 latex语法的
    EQUATION("equation", BlockFactory::createEquationBlock),
    QUOTE("quote", BlockFactory::createQuoteBlock),
    CODE("code", BlockFactory::createCodeBlock),
    // 提醒？
    CALLOUT("callout", BlockFactory::createCalloutBlock),

    // a link
    EMBED("embed", BlockFactory::createEmbedBlock),
    BOOKMARK("bookmark", BlockFactory::createBookmarkBlock),

    // a file
    AUDIO("audio", BlockFactory::createUnknownBlock),
    IMAGE("image", BlockFactory::createUnknownBlock),
    FILE("file", BlockFactory::createUnknownBlock),
    VIDEO("video", BlockFactory::createUnknownBlock),


    //block lists （item）
    TOGGLE("toggle", BlockFactory::createToggleBlock),
    TODO("to_do", BlockFactory::createTodoBlock),
    NUMBEREDLIST("numbered_list", BlockFactory::createNumberListBlock),
    BULLETEDLIST("bulleted_list", BlockFactory::createBulletedListBlock),

    // 下面这些包含一个block 数组
    //layout lists 布局用
    COLUMNLIST("column_list", BlockFactory::createUnknownBlock),
    COLUMN("column", BlockFactory::createUnknownBlock),
    //page
    PAGE("page", BlockFactory::createPageBlock),
    //按钮模板，相当于一个页面
    FACTORY("factory", BlockFactory::createPageBlock),


    //nothing
    DIVIDER("divider", BlockFactory::createDividerBlock),
    //目录
    TABLEOFCONTENTS("table_of_contents", BlockFactory::createTableOfContentsBlock),
    //导航栏
    BREADCRUMB("breadcrumb", BlockFactory::createBreadcrumbBlock),

    //table
    COLLECTIONVIEWPAGE("collection_view_page", BlockFactory::createCollectionViewPageBlock),
    COLLECTIONVIEW("collection_view", BlockFactory::createCollectionViewBlock),


    //不想弄的
    // 谷歌地图
    MAPS("maps", BlockFactory::createUnknownBlock),
    // google drive （反正也用不了，不弄了）
    DRIVE("drive", BlockFactory::createUnknownBlock),
    // github 的gist  同上，用不了：）
    GIST("gist", BlockFactory::createUnknownBlock),
    // 同上上
    TWEET("tweet", BlockFactory::createUnknownBlock),

    UNKNOWN("unknown", BlockFactory::createUnknownBlock);

    public Function<Parser.JsonInfo, Block> getBlockSupplier() {
        return blockSupplier;
    }

    private static class AllType {
        static HashMap<String, BlockType> allType = new HashMap<String, BlockType>();
    }


    private String name;
    private Function<Parser.JsonInfo, Block> blockSupplier;

    BlockType(String name, Function<Parser.JsonInfo, Block> blockSupplier) {
        AllType.allType.put(name, this);
        this.name = name;
        this.blockSupplier = blockSupplier;
    }

    public static BlockType getTypeByName(String name) {
        return AllType.allType.getOrDefault(name, UNKNOWN);
    }
}
