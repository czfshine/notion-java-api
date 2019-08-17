package cn.czfshine.notion.model.block;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageBlock extends Block {
    private RichText title;
    private List<Block> contents = new ArrayList<>();
    private List<String> contentIds;

    public void addBlockContent(Block block) {
        contents.add(block);
    }
}
