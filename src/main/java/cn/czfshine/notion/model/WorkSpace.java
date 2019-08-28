package cn.czfshine.notion.model;

import cn.czfshine.notion.model.block.Block;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkSpace extends Block {

    private String name = "";
    private String domain = "";
    private List<User> users = new ArrayList<>();
    private List<Page> pages = new ArrayList<>();
    private List<String> pageIds = new ArrayList<>();

    public List<Page> getPages() {
        return pages;
    }
}
