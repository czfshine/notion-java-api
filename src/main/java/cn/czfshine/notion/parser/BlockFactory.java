package cn.czfshine.notion.parser;

import cn.czfshine.notion.EquationBlock;
import cn.czfshine.notion.model.*;
import cn.czfshine.notion.parser.Parser.JsonInfo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BlockFactory {

    private static void setBasicInfo(Block block, JsonInfo info) {
        //todo 完善
    }

    private static RichText getRichByInfoFromTitle(@NotNull JsonInfo info) {
        if (info.value.properties == null) {
            return new RichText();
        }
        Object title = info.value.properties.get("title");
        return new RichText((ArrayList) title);
    }

    @NotNull
    public static PageBlock createPageBlock(JsonInfo info) {
        PageBlock pageBlock = new PageBlock();
        setBasicInfo(pageBlock, info);
        pageBlock.setTitle(getRichByInfoFromTitle(info));
        return pageBlock;
    }

    @NotNull
    public static TextBlock createTextBlock(JsonInfo info) {
        TextBlock textBlock = new TextBlock();
        setBasicInfo(textBlock, info);
        textBlock.setText(getRichByInfoFromTitle(info));
        return textBlock;
    }

    @NotNull
    public static TodoBlock createTodoBlock(JsonInfo info) {
        TodoBlock todoBlock = new TodoBlock();
        setBasicInfo(todoBlock, info);
        todoBlock.setText(getRichByInfoFromTitle(info));
        //todo 数组校验
        //[["Yes"]]
        if (info.value.properties != null && info.value.properties.get("checked") != null) {
            todoBlock.setChecked(true);
        }
        return todoBlock;
    }

    @NotNull
    public static HeaderBlock createHeaderBlock(JsonInfo info) {
        HeaderBlock headerBlock = new HeaderBlock();
        setBasicInfo(headerBlock, info);
        headerBlock.setText(getRichByInfoFromTitle(info));
        BlockType blockType = BlockType.getTypeByName(info.value.type);
        switch (blockType) {
            case HEADER: {
                headerBlock.setLevel((byte) 1);
                break;
            }
            case SUBHEADER: {
                headerBlock.setLevel((byte) 2);
                break;
            }
            case SUBSUBHEADER: {
                headerBlock.setLevel((byte) 3);
                break;
            }
            default: {

            }
        }
        return headerBlock;
    }

    @NotNull
    public static BulletedListBlock createBulletedListBlock(JsonInfo info) {
        BulletedListBlock bulletedListBlock = new BulletedListBlock();
        setBasicInfo(bulletedListBlock, info);
        bulletedListBlock.setText(getRichByInfoFromTitle(info));
        return bulletedListBlock;
    }

    @NotNull
    public static NumberListBlock createNumberListBlock(JsonInfo info) {
        NumberListBlock numberListBlock = new NumberListBlock();
        setBasicInfo(numberListBlock, info);
        numberListBlock.setText(getRichByInfoFromTitle(info));
        return numberListBlock;
    }

    @NotNull
    public static ToggleBlock createToggleBlock(JsonInfo info) {
        ToggleBlock toggleBlock = new ToggleBlock();
        setBasicInfo(toggleBlock, info);
        toggleBlock.setTitle(getRichByInfoFromTitle(info));
        List<String> content = info.value.content;

        if (content != null) {
            toggleBlock.setContentIds(content);
        }
        return toggleBlock;
    }

    @NotNull
    public static QuoteBlock createQuoteBlock(JsonInfo info) {
        QuoteBlock quoteBlock = new QuoteBlock();
        setBasicInfo(quoteBlock, info);
        quoteBlock.setText(getRichByInfoFromTitle(info));
        return quoteBlock;
    }

    @NotNull
    public static DividerBlock createDividerBlock(JsonInfo info) {
        DividerBlock dividerBlock = new DividerBlock();
        setBasicInfo(dividerBlock, info);
        return dividerBlock;
    }

    @NotNull
    public static CollectionViewBlock createCollectionViewBlock(JsonInfo info) {
        CollectionViewBlock collectionViewBlock = new CollectionViewBlock();
        setBasicInfo(collectionViewBlock, info);
        //todo
        return collectionViewBlock;
    }

    @NotNull
    public static CollectionViewPageBlock createCollectionViewPageBlock(JsonInfo info) {
        CollectionViewPageBlock collectionViewPageBlock = new CollectionViewPageBlock();
        setBasicInfo(collectionViewPageBlock, info);
        //todo
        return collectionViewPageBlock;
    }

    @Nullable
    private static String getFirstStringFromPropertiesStringArray(@NotNull JsonInfo info, String propertiesName, String className) {
        Object source = info.value.properties.get(propertiesName);
        if (source != null && source instanceof List) {
            Object o = ((List) source).get(0);
            if (o instanceof String) {
                return (String) o;
            } else {
                log.warn("{}.properties.link must string list,but get item type:{}", className, o.getClass().getName());
            }
        } else {
            log.warn("{}}.properties.link must string list,but get:{}", className, source);
        }
        return null;
    }

    private static void setUrlFromPropertiesStringArray(@NotNull AUrlBlock aUrlBlock, JsonInfo info, String propertiesName) {
        String url = getFirstStringFromPropertiesStringArray(info, propertiesName, aUrlBlock.getClass().getName());
        aUrlBlock.setUrl(url);
    }

    @NotNull
    public static BookmarkBlock createBookmarkBlock(JsonInfo info) {
        BookmarkBlock bookmarkBlock = new BookmarkBlock();
        setUrlFromPropertiesStringArray(bookmarkBlock, info, "link");
        return bookmarkBlock;
    }

    @NotNull
    public static ImageBlock createImageBlock(JsonInfo info) {
        ImageBlock imageBlock = new ImageBlock();
        setBasicInfo(imageBlock, info);
        setUrlFromPropertiesStringArray(imageBlock, info, "source");
        return imageBlock;
    }

    @NotNull
    public static CodeBlock createCodeBlock(JsonInfo info) {
        CodeBlock codeBlock = new CodeBlock();
        String language = getFirstStringFromPropertiesStringArray(info, "language", codeBlock.getClass().getName());
        codeBlock.setLanguage(language);
        return codeBlock;
    }

    @NotNull
    public static EmbedBlock createEmbedBlock(JsonInfo info) {
        EmbedBlock embedBlock = new EmbedBlock();

        setUrlFromPropertiesStringArray(embedBlock, info, "source");

        JsonInfo.Value.Format format = info.value.format;
        if (format != null) {
            embedBlock.setBlockFullWidth(format.block_full_width);
            embedBlock.setBlockHeight(format.block_height);
            embedBlock.setBlockWidth(format.block_width);
            embedBlock.setBlockPageWidth(format.block_page_width);
            embedBlock.setBlockPreserveScale(format.block_preserve_scale);
        } else {
            log.warn("the value.format is null,:{}", info.toString());
        }

        return embedBlock;
    }

    public static UnknowBlock createUnknownBlock(JsonInfo info) {
        return new UnknowBlock();
    }

    @NotNull
    public static TableOfContentsBlock createTableOfContentsBlock(JsonInfo info) {
        TableOfContentsBlock tableOfContentsBlock = new TableOfContentsBlock();
        return tableOfContentsBlock;
    }

    @NotNull
    public static EquationBlock createEquationBlock(JsonInfo info) {
        EquationBlock equationBlock = new EquationBlock();
        equationBlock.setText(getRichByInfoFromTitle(info));
        return equationBlock;
    }

    @NotNull
    public static BreadcrumbBlock createBreadcrumbBlock(JsonInfo info) {
        BreadcrumbBlock breadcrumbBlock = new BreadcrumbBlock();
        return breadcrumbBlock;
    }

    @NotNull
    public static CalloutBlock createCalloutBlock(JsonInfo info) {
        CalloutBlock calloutBlock = new CalloutBlock();

        calloutBlock.setText(getRichByInfoFromTitle(info));

        JsonInfo.Value.Format format = info.value.format;
        if (format == null) {
            log.warn("the value.format is null,:{}", info.toString());
        } else {
            calloutBlock.setColor(format.block_color);
            calloutBlock.setIcon(format.page_icon);
        }
        return calloutBlock;
    }

    @NotNull
    public static Block createBlockByType(@NotNull JsonInfo info) {
        BlockType blockType = BlockType.getTypeByName(info.value.type);
        return blockType.getBlockSupplier().apply(info);
    }

}
