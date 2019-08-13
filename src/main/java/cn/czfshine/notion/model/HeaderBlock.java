package cn.czfshine.notion.model;

import lombok.Data;

@Data
public class HeaderBlock extends TextBlock {
    private byte level = 1; // 1:h1 2:h2 3:h3
}
