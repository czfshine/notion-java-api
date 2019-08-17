package cn.czfshine.notion.model.block;

import lombok.Data;

@Data
public class Block {
    private String id = "";
    private int version = -1;
    private BlockType blockType = BlockType.UNKNOWN;

}
