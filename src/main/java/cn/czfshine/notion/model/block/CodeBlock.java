package cn.czfshine.notion.model.block;

import lombok.Data;

@Data
public class CodeBlock extends TextBlock {
    private String language;
}
