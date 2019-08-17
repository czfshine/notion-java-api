package cn.czfshine.notion.web;

import cn.czfshine.notion.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author:czfshine
 * @date:2019/6/30 1:21
 */
@Slf4j
public class NotionClientV3Impl implements NotionClientV3 {
    OkHttpClient okHttpClient = new OkHttpClient();
    String token;

    {
        URL resource = NotionClientV3Impl.class.getResource("/token.properties");
        try {
            InputStream inputStream = resource.openStream();
            Properties properties = new Properties();
            properties.load(inputStream);
            String tokenv2 = properties.getProperty("tokenv2");
            if (tokenv2 != null) {
                token = tokenv2;
            } else {
                log.error("配置文件错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String baseurl = "https://www.notion.so/api/v3/";

    private String post(String url, String json) throws IOException {
        Request build = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .addHeader("cookie", "token_v2=" + token + "; ")
                .build();

        Response execute = okHttpClient.newCall(build).execute();

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(execute.body().string()).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    /*
    The Api in javascript
        t.getAssetsJson = L("getAssetsJson"),
        t.getAsanaWorkspaces = L("getAsanaWorkspaces"),
        t.getTrelloBoards = L("getTrelloBoards"),
        t.duplicateBlock = F("duplicateBlock"),
        t.findUser = L("findUser"),
        t.searchBlocks = L("searchBlocks"),
        t.searchGoogleContacts = L("searchGoogleContacts"),
        t.searchPages = L("searchPages"),
        t.searchPagesWithParent = L("searchPagesWithParent"),
        t.searchCollections = L("searchCollections"),
        t.searchTrashPages = L("searchTrashPages"),
        t.createEmailUser = L("createEmailUser"),
        t.updateSubscription = L("updateSubscription"),
        t.sendMobileAppLink = L("sendMobileAppLink"),
        t.getDesktopDownloadUrl = L("getDesktopDownloadUrl"),
        t.cancelSubscription = L("cancelSubscription"),
        t.getSubscriptionData = L("getSubscriptionData"),
        t.importFile = F("importFile"),
        t.importTrello = F("importTrello"),
        t.importAsana = F("importAsana"),
        t.importEvernote = F("importEvernote"),
        t.exportBlock = F("exportBlock"),
        t.exportCollection = F("exportCollection"),
        t.enqueueTask = L("enqueueTask"),
        t.loginWithEmail = L("loginWithEmail"),
        t.loginWithGoogleJwt = L("loginWithGoogleJwt"),
        t.loginWithGoogleAuth = L("loginWithGoogleAuth"),
        t.logout = L("logout"),
        t.addWebClipperFile = L("addWebClipperFile"),
        t.sendTemporaryPassword = L("sendTemporaryPassword"),
        t.isGoogleAppsEmail = L("isGoogleAppsEmail"),
        t.disconnectAsana = L("disconnectAsana"),
        t.disconnectTrello = L("disconnectTrello"),
        t.disconnectEvernote = L("disconnectEvernote"),
        t.disconnectDrive = L("disconnectDrive"),
        t.getActivityLog = L("getActivityLog"),
        t.changeEmail = L("changeEmail"),
        t.sendEmailVerification = L("sendEmailVerification"),
        t.getUploadFileUrl = L("getUploadFileUrl"),
        t.deleteSpace = F("deleteSpace"),
        t.deleteUser = L("deleteUser"),
        t.getPublicPageData = L("getPublicPageData"),
        t.adminDeleteUser = L("adminDeleteUser"),
        t.getInvoiceData = L("getInvoiceData"),
        t.addUsersToSpace = L("addUsersToSpace"),
        t.removeUsersFromSpace = L("removeUsersFromSpace"),
        t.getJoinableSpaces = L("getJoinableSpaces"),
        t.deleteBlocks = L("deleteBlocks"),
        t.getUserAnalyticsSettings = L("getUserAnalyticsSettings"),
        t.submitTransaction = L("submitTransaction");
        var N = L("getRecordValues");
        t.loadUserContent = L("loadUserContent"),
        t.loadPageChunk = L("loadPageChunk"),
        t.queryCollection = L("queryCollection"),
        t.renameGroup = F("renameGroup"),
        t.deleteGroup = F("deleteGroup"),
        t.getSelectPropertyValues = L("getSelectPropertyValues"),
        t.getNotificationLog = L("getNotificationLog"),
        t.getUserSharedPages = L("getUserSharedPages"),
        t.setNotificationsAsRead = L("setNotificationsAsRead"),
        t.getUserNotifications = L("getUserNotifications"),
        t.setPageNotificationsAsReadAndVisited = L("setPageNotificationsAsReadAndVisited"),
        t.setSpaceInviteNotificationsAsReadAndVisited = L("setSpaceInviteNotificationsAsReadAndVisited"),
        t.unsubscribeFromEmails = L("unsubscribeFromEmails"),
        t.leaveBlock = L("leaveBlock"),
        t.getPublicSpaceData = L("getPublicSpaceData"),
        t.loadBlockSubtree = L("loadBlockSubtree"),
        t.initializeUserAnalytics = L("initializeUserAnalytics");
        var U = L("getSignedFileUrls");
        t.getBillingHistory = L("getBillingHistory"),
        t.exportSpace = F("exportSpace"),
        t.transferPermissionGroup = L("transferPermissionGroup"),
        t.disableUserAnalytics = L("disableUserAnalytics"),
        t.createReferral = L("createReferral"),
        t.unsubscribeFromIntercomEmails = L("unsubscribeFromIntercomEmails"),
        t.getSnapshotsList = L("getSnapshotsList"),
        t.getSnapshot = L("getSnapshot"),
        t.restoreSnapshot = F("restoreSnapshot"),
        t.sendReferralEmail = L("sendReferralEmail"),
        t.activateReferral = L("activateReferral"),
        t.setBookmarkMetadata = L("setBookmarkMetadata"),
        t.authWithAsana = L("authWithAsana"),
        t.getConnectedAppsStatus = L("getConnectedAppsStatus"),
        t.authWithTrello = L("authWithTrello"),
        t.authWithSlack = L("authWithSlack"),
        t.authWithGoogleForDrive = L("authWithGoogleForDrive"),
        t.getGoogleDriveAccounts = L("getGoogleDriveAccounts"),
        t.initializeGoogleDriveBlock = L("initializeGoogleDriveBlock"),
        t.refreshGoogleDriveBlock = L("refreshGoogleDriveBlock"),
        t.getEvernoteNotebooks = L("getEvernoteNotebooks"),
        t.getWebClipperData = L("getWebClipperData"),
        t.addWebClipperURLs = L("addWebClipperURLs"),
        t.searchWebClipperPages = L("searchWebClipperPages"),
        t.authWithEvernote = L("authWithEvernote"),
        t.recoverUser = L("recoverUser"),
        t.recoverSpace = L("recoverSpace"),
        t.revokeUserTokens = L("revokeUserTokens"),
        t.exportBlockPreview = L("exportBlockPreview"),
        t.adminSearchUsers = L("adminSearchUsers"),
        t.adminFindDeletedUser = L("adminFindDeletedUser"),
        t.adminWhitelistCreditDomain = L("adminWhitelistCreditDomain"),
        t.searchUnsplashImages = L("searchUnsplashImages"),
        t.adminSearchSpaces = L("adminSearchSpaces");
        var W = L("getUserTasks")
          , z = L("getTasks")
     */

    /**
     * @param pageid
     * @return
     * @throws IOException
     */
    public String loadPage(String pageid) throws IOException {
        String endpoint = "loadPageChunk";
        String data = "{\"pageId\":\"" + pageid + "\"," +
                "\"limit\":50," +
                "\"cursor\":{\"stack\":[]}," +
                "\"chunkNumber\":0," +
                "\"verticalColumns\":false}";
        System.out.println(data);
        return post(baseurl + endpoint, data);
    }

    public String getUserAnalyticsSettings() throws IOException {
        String endpoint = "getUserAnalyticsSettings";
        String data = "{\"platform\":\"web\"}";
        System.out.println(data);
        return post(baseurl + endpoint, data);
    }

    public String loadUserContent() throws IOException {
        String endpoint = "loadUserContent";
        return post(baseurl + endpoint, "{}");
    }

    @Override
    public User getUser() {
        throw new NotImplementedException();
    }
}
