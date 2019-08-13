package cn.czfshine.notion.model;

import lombok.Data;

@Data
public class TodoBlock extends TextBlock {
    private boolean checked = false;
}
