package cn.czfshine.notion.web;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class Primus {

    private final static String dictionary = "abcdefghijklmnopqrstuvwxyz0123456789_";
    private final String baseUrl;
    private final String url;
    private final Queue<String> messages;
    private int reconnectAttempt;
    public WebSocket mWebSocket;
    private PrimusDataCallback dataCallback;
    private PrimusOpenCallback openCallback;
    private PrimusWebSocketCallback websocketCallback;
    private boolean autoReconnect;
    private int currentTimerRun;
    private static final String INTENTIONAL_SERVER_END = "\"primus::server::close\"";

    Runnable heartbeatTask = new Runnable() {
        @Override
        public void run() {
            currentTimerRun++;
            // send a heartbeat every 10 seconds
            if (!websocketIsOpen()) {
                currentTimerRun = 0;

                reconnect();
            }
            if (currentTimerRun >= 10) {
                currentTimerRun = 0;
                String ping = "primus::ping::" + System.currentTimeMillis();
                sendRawToWebSocket(ping);
            } else {
            }
            scheduleHeartbeat();
        }
    };
    private boolean connecting;

    private Primus(String baseUrl) {

        this.baseUrl = baseUrl;
        url = generatePrimusUrl();
        currentTimerRun = 0;
        messages = new LinkedList<String>();
        connecting = true;
        autoReconnect = true;
        reconnectAttempt = 0;
        createNewWebsocket.run();
    }

    public static Primus connect(String url) {
        return new Primus(url);
    }

    public void setDataCallback(PrimusDataCallback callback) {
        this.dataCallback = callback;
    }

    public void setWebSocketCallback(PrimusWebSocketCallback callback) {
        this.websocketCallback = callback;
    }

    public void setOpenCallback(PrimusOpenCallback callback) {
        this.openCallback = callback;
    }

    private String getProtocolFromUrl() {
        if (url.contains("https://")) {
            return "wss";
        } else {
            return "ws";
        }
    }

    Runnable createNewWebsocket = new Runnable() {
        @Override
        public void run() {

            AsyncHttpClient.getDefaultInstance().websocket(url, getProtocolFromUrl(), (ex, returnedWebsocket) -> {
                connecting = false;

                if (ex != null) {
                    ex.printStackTrace();
                    reconnect();
                    return;
                }

                reconnectAttempt = 0;
                mWebSocket = returnedWebsocket;
                mWebSocket.setDataCallback((dataEmitter, byteBufferList) -> {
                    // note that this data has been read
                    byteBufferList.recycle();
                });

                mWebSocket.setClosedCallback(e -> {
                    if (e != null) {
                        e.printStackTrace();
                        return;
                    }

                    if (autoReconnect) {
                        reconnect();
                    } else {
                        cancelHeartbeat();
                    }
                });

                mWebSocket.setEndCallback(e -> {
                    if (e != null) {
                        e.printStackTrace();
                        return;
                    }
                });
                mWebSocket.setStringCallback(s -> {

                    try {
                        JSONObject response;
                        if (s.startsWith("o")) {
                            if (openCallback != null) {
                                openCallback.onOpen();
                            }
                        } else if (s.startsWith("a")) {

                            s = s.substring(1);
                            JSONArray r = new JSONArray(s);

                            String data = r.getString(0);
                            if (data.charAt(0) == '"') {
                                if (data.equals(INTENTIONAL_SERVER_END)) {
                                    autoReconnect = false;

                                }
                                // a json message
                            } else {
                                response = new JSONObject(data);

                                if (dataCallback != null) {
                                    dataCallback.onData(response);
                                }
                            }

                        } else {

                            return;
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                });
                scheduleHeartbeat();
                if (websocketCallback != null) {
                    websocketCallback.onWebSocket(mWebSocket);
                }

            });

        }
    };

    private void scheduleHeartbeat() {
        //todo
        heartbeatTask.run();
    }

    private void cancelHeartbeat() {
        //mHandler.removeCallbacks(heartbeatTask);
    }

    private char randomCharacterFromDictionary() {
        int rand = (int) (Math.random() * dictionary.length());
        return dictionary.charAt(rand);
    }

    private String randomStringOfLength(int length) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            s.append(randomCharacterFromDictionary());
        }
        return s.toString();
    }

    // "ws://cine-io-signaling.herokuapp.com/primus/211/b9__ftym/websocket"
    private String generatePrimusUrl() {
        Random r = new Random();
        int server = r.nextInt(1000);
        String connId = randomStringOfLength(8);
        return baseUrl + "/" + server + "/" + connId + "/websocket";
    }

    private void sendRawToWebSocket(String data) {
        data = "[\"" + data + "\"]";

        scheduleMessage(data);
    }

    public void end() {
        autoReconnect = false;
        cancelHeartBeatAndCloseWebSocket();
    }

    private void cancelHeartBeatAndCloseWebSocket() {
        cancelHeartbeat();
        if (mWebSocket != null) {
            mWebSocket.end();
        }
    }

    public void send(final JSONObject j) {
        try {

            scheduleMessage(j.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void scheduleMessage(String j) {
        messages.add(j);
        processScheduledMessages();
    }

    private void processScheduledMessages() {
//        unfortunately there seems to be a broken pipe issue.
        boolean brokenPipe = false;
        if (websocketIsOpen()) {
            while (!brokenPipe && !messages.isEmpty()) {
                String message = messages.remove();

                actuallySendMessage(message);
            }
        } else if (autoReconnect) {
            reconnect();
        } else {
        }
    }

    private boolean websocketIsOpen() {
        if (mWebSocket == null) {
            return false;
        }
        return mWebSocket.isOpen();
    }

    private void reconnect() {

        // if we're already connecting, don't try to autoReconnect;
        if (connecting) {
            return;
        }
        reconnectAttempt += 1;
        connecting = true;
        cancelHeartBeatAndCloseWebSocket();
        long millisecondsToDelay = calculateRetryDelay();

        // mHandler.postDelayed(createNewWebsocket, millisecondsToDelay);
    }

    // http://dthain.blogspot.nl/2009/02/exponential-backoff-in-distributed.html
    private long calculateRetryDelay() {
        int minDelay = 500;
        int factor = 2;
        long millisecondsToDelay = Math.round((Math.random() + 1) * minDelay * Math.pow(factor, reconnectAttempt));
        return millisecondsToDelay;
    }

    private void actuallySendMessage(final String message) {

        mWebSocket.send(message);

    }


    public static interface PrimusWebSocketCallback {
        public void onWebSocket(WebSocket websocket);
    }

    public static interface PrimusOpenCallback {
        public void onOpen();
    }

    public static interface PrimusDataCallback {
        public void onData(JSONObject jsonObject);
    }
}