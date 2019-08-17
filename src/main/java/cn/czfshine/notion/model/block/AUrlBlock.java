package cn.czfshine.notion.model.block;

import lombok.Data;

@Data
public abstract class AUrlBlock extends Block {
    private String url;
}
