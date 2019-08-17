package cn.czfshine.notion.model.block;

import lombok.Data;

@Data
public class TodoBlock extends TextBlock {
    private boolean checked = false;
}
