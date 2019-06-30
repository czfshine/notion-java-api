package cn.czfshine.notion.web;


import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * @author:czfshine
 * @date:2019/6/30 0:47
 */

public class NotionRequest {


    public static void main(String args[]) throws IOException {
        System.out.println(new NotionClient().loadPage("ae9ff493-017b-4cf2-8ca5-9a3a9efa88e8"));
    }
}
