package cn.czfshine.notion.model;

import lombok.Data;

@Data
public class Block {
    private String id = "";
    private int version = -1;
    private BlockType blockType = BlockType.UNKNOWN;

}
