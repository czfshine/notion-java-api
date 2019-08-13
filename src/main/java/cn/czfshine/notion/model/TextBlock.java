package cn.czfshine.notion.model;

public class TextBlock {

    private String text;

    public TextBlock(String text) {
        this.text = text;
    }

    public TextBlock() {

    }

    /**
     * 获取纯文本
     *
     * @return
     */
    public String getPlainText() {
        return text;
    }

}
