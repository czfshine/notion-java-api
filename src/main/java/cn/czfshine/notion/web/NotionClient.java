package cn.czfshine.notion.web;

import okhttp3.*;

import java.io.IOException;

/**
 * @author:czfshine
 * @date:2019/6/30 1:21
 */

public class NotionClient {
    OkHttpClient okHttpClient = new OkHttpClient();
    String token = "c8220ce21d08a4475bffa5a477c097b8" +
            "cd990c74a8ec38df9f3b44d92bba5cdf0b63ddc" +
            "4bfc4d5d808c38ed405ea2f6e" +
            "9a6ed9fce93cc85b69948aef1de261c68" +
            "9efcbaab068325ea859b2e14a24";
    private String baseurl = "https://www.notion.so/api/v3/";
    private String post(String url,String json) throws IOException {
        Request build = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .addHeader("cookie","token_v2=c8220ce21d08a4475bffa5a477c097b8cd990c74a8ec38df9f3b44d92bba5cdf0b63ddc4bfc4d5d808c38ed405ea2f6e9a6ed9fce93cc85b69948aef1de261c689efcbaab068325ea859b2e14a24; ")
                .build();

        Response execute = okHttpClient.newCall(build).execute();
        return execute.body().string();
    }

    public String loadPage(String pageid) throws IOException {
        String endpoint="loadPageChunk";
        String data = "{\"pageId\":\""+pageid+"\"," +
                "\"limit\":50," +
                "\"cursor\":{\"stack\":[]}," +
                "\"chunkNumber\":0," +
                "\"verticalColumns\":false}";
        System.out.println(data);
        return post(baseurl+endpoint,data);
    }
}
