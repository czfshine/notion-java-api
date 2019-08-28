package cn.czfshine.notion.model;


import cn.czfshine.notion.model.block.Block;
import cn.czfshine.notion.model.block.PageBlock;
import cn.czfshine.notion.model.block.RichText;

import java.util.List;

/**
 * 一个页面的所有信息
 */
public class Page {
    private List<Block> blocks;
    private PageBlock pageBlock;

    private RichText title;

    public Page(PageBlock pageBlock) {
        this.pageBlock = pageBlock;
        title = pageBlock.getTitle();
    }

    private String toPlainText() {
        StringBuilder sb = new StringBuilder();

        sb.append("[title]:");
        sb.append(title.toString());
        sb.append("\n");

        blocks.forEach((block) -> {
            sb.append("[block]:");
            sb.append(block.toString());
            sb.append("\n");
        });

        return sb.toString();
    }

    @Override
    public String toString() {
        return toPlainText();
    }
}
