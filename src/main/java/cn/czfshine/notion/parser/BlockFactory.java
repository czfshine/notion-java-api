package cn.czfshine.notion.parser;

import cn.czfshine.notion.model.*;
import cn.czfshine.notion.parser.Parser.JsonInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BlockFactory {

    private static void setBasicInfo(Block block, JsonInfo info) {
        //todo 完善
    }

    private static RichText getRichByInfoFromTitle(JsonInfo info) {
        if (info.value.properties == null) {
            return new RichText();
        }
        Object title = info.value.properties.get("title");
        return new RichText((ArrayList) title);
    }

    public static PageBlock createPageBlock(JsonInfo info) {
        PageBlock pageBlock = new PageBlock();
        setBasicInfo(pageBlock, info);
        pageBlock.setTitle(getRichByInfoFromTitle(info));
        return pageBlock;
    }

    public static TextBlock createTextBlock(JsonInfo info) {
        TextBlock textBlock = new TextBlock();
        setBasicInfo(textBlock, info);
        textBlock.setText(getRichByInfoFromTitle(info));
        return textBlock;
    }

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

    public static HeaderBlock createHeaderBlock(JsonInfo info, BlockType blockType) {
        HeaderBlock headerBlock = new HeaderBlock();
        setBasicInfo(headerBlock, info);
        headerBlock.setText(getRichByInfoFromTitle(info));
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

    public static BulletedListBlock createBulletedListBlock(JsonInfo info) {
        BulletedListBlock bulletedListBlock = new BulletedListBlock();
        setBasicInfo(bulletedListBlock, info);
        bulletedListBlock.setText(getRichByInfoFromTitle(info));
        return bulletedListBlock;
    }

    public static NumberListBlock createNumberListBlock(JsonInfo info) {
        NumberListBlock numberListBlock = new NumberListBlock();
        setBasicInfo(numberListBlock, info);
        numberListBlock.setText(getRichByInfoFromTitle(info));
        return numberListBlock;
    }

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

    public static QuoteBlock createQuoteBlock(JsonInfo info) {
        QuoteBlock quoteBlock = new QuoteBlock();
        setBasicInfo(quoteBlock, info);
        quoteBlock.setText(getRichByInfoFromTitle(info));
        return quoteBlock;
    }

    public static DividerBlock createDividerBlock(JsonInfo info) {
        DividerBlock dividerBlock = new DividerBlock();
        setBasicInfo(dividerBlock, info);
        return dividerBlock;
    }

    public static Block createBlockByType(JsonInfo info, BlockType blockType) {
        switch (blockType) {
            case TEXT: {
                return createTextBlock(info);
            }
            case TODO: {
                return createTodoBlock(info);
            }
            case HEADER:
            case SUBHEADER:
            case SUBSUBHEADER: {
                return createHeaderBlock(info, blockType);
            }
            case BULLETEDLIST: {
                return createBulletedListBlock(info);
            }
            case NUMBEREDLIST: {
                return createNumberListBlock(info);
            }
            case TOGGLE: {
                return createToggleBlock(info);
            }
            case QUOTE: {
                return createQuoteBlock(info);
            }


            default: {
                return null;
            }
        }
    }


}
