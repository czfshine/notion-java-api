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
    SUBSUBHEADER("sub_sub_header", BlockFactory::createHeaderBlock),
    EQUATION("equation", BlockFactory::createEquationBlock),
    QUOTE("quote", BlockFactory::createQuoteBlock),
    CODE("code", BlockFactory::createCodeBlock),
    CALLOUT("callout", BlockFactory::createCalloutBlock),

    // a link
    EMBED("embed", BlockFactory::createEmbedBlock),
    BOOKMARK("bookmark", BlockFactory::createBookmarkBlock),

    // a file
    AUDIO("audio", BlockFactory::createUnknownBlock),
    IMAGE("image", BlockFactory::createUnknownBlock),
    FILE("file", BlockFactory::createUnknownBlock),
    VIDEO("video", BlockFactory::createUnknownBlock),


    //lists
    TOGGLE("toggle", BlockFactory::createToggleBlock),
    TODO("to_do", BlockFactory::createTodoBlock),
    NUMBEREDLIST("numbered_list", BlockFactory::createNumberListBlock),
    BULLETEDLIST("bulleted_list", BlockFactory::createBulletedListBlock),

    //nothing
    DIVIDER("divider", BlockFactory::createDividerBlock),
    TABLEOFCONTENTS("table_of_contents", BlockFactory::createTableOfContentsBlock),
    BREADCRUMB("breadcrumb", BlockFactory::createBreadcrumbBlock),

    //table
    COLLECTIONVIEWPAGE("collection_view_page", BlockFactory::createCollectionViewPageBlock),
    COLLECTIONVIEW("collection_view", BlockFactory::createCollectionViewBlock),

    //page
    PAGE("page", BlockFactory::createPageBlock),
    FACTORY("factory", BlockFactory::createPageBlock),


    //不想弄的
    MAPS("maps", BlockFactory::createUnknownBlock),
    DRIVE("drive", BlockFactory::createUnknownBlock),
    GIST("gist", BlockFactory::createUnknownBlock),
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
