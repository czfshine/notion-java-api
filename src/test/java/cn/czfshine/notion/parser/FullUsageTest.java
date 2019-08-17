package cn.czfshine.notion.parser;

import cn.czfshine.notion.model.Page;
import cn.czfshine.notion.model.User;
import cn.czfshine.notion.web.NotionClientV3;
import cn.czfshine.notion.web.NotionClientV3Impl;

import java.util.List;

public class FullUsageTest {

    private void getData() {
        NotionClientV3 notionClientV3 = new NotionClientV3Impl();
        User user = notionClientV3.getUser();
        List<Page> pages = user.getWorkSpaces().get(0).getPages();


    }
}
