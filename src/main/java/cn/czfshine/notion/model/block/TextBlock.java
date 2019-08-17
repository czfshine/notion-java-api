package cn.czfshine.notion.model.block;

import lombok.Data;

@Data
public class TextBlock extends Block {

    private RichText text;
    public TextBlock() {

    }
    /**
     * 获取纯文本
     *
     * @return
     */
    public String getPlainText() {
        return text.getTitle();
    }

}
