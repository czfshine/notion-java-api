package cn.czfshine.notion.web;

import java.io.IOException;

/**
 * @author:czfshine
 * @date:2019/6/30 0:47
 */

public class NotionRequest {


    public static void main(String args[]) throws IOException {
        NotionClientV3 notionClientV3 = new NotionClientV3();
        //System.out.println(notionClientV3.loadPage("ae9ff493-017b-4cf2-8ca5-9a3a9efa88e8"));
        // System.out.println(notionClientV3.getUserAnalyticsSettings());
        System.out.println(notionClientV3.loadUserContent());
//
//        Primus primus;
//        String PRIMUS_URL = "https://msgstore.www.notion.so/primus/?sessionId=439d15a6-32d4-49af-ae5e-8332e4459d92&_primuscb=MkezNmO&EIO=3&transport=polling&t=Mke_FJ7&b64=1&sid=lsgerlFJwNL6rb96BKp6";
//
//            primus = Primus.connect(this, PRIMUS_URL);
//            Primus.PrimusOpenCallback openCallback = new Primus.PrimusOpenCallback() {
//                @Override
//                public void onOpen() {
//                    // Websocket open
//                }
//            };
//            Primus.PrimusDataCallback dataCallback = new Primus.PrimusDataCallback() {
//                @Override
//                public void onData(JSONObject data) {
//                    //got data
//                }
//            };
//            primus.setOpenCallback(openCallback);
//            primus.setDataCallback(dataCallback);
//        }
    }
}

