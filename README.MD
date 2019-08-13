# Notion.so Java Api

（非官方）notion.so的java版api，通过追踪网络请求解析出来的，不完善.

最新解析时间：2019/08/12 （以后太久远的可能有些接口变了）

解析时的版本：V3

## 架构

* 第一层是原始的网络请求层，基本上一个函数对应一个解析出来的url
* 第二层是实用层，对第一层的网络请求参数进行封装，设定默认值，组合多个请求实现某个通用功能，通常使用这一层的函数
* 第三层是较高层次的逻辑，包括事件通知，数据的双向绑定等

## 原始API描述

> 这些是从javascript代码和网络请求截取的api相关信息，加上自己对它的理解，可能不完善或者根本是错的，已经按照我认为的使用频率排序了，有些感觉没用的就没写在代码里面

| 连接  | 描述  | 备注  |
|---|---|---|
| getRecordValues | 获取一个或多个块/记录的信息 | 在notion里面，所有元素都对应成block，<br />每个block都有一个UUID对应，通过这个接口发送UUID给后端<br />获取对应BLOCK的信息，具体参数看代码 |
|exportBlockPreview| | |
|getWebClipperData| | |
|addWebClipperURLs| | |
|setBookmarkMetadata| | |
|exportSpace| | |
|leaveBlock| | |
|getPublicSpaceData| | |
|loadBlockSubtree| | |
|getNotificationLog| | |
|getUserSharedPages| | |
|setNotificationsAsRead| | |
|getUserNotifications| | |
|loadUserContent| | |
|loadPageChunk| | |
|queryCollection| | |
|renameGroup| | |
|deleteGroup| | |
|getSelectPropertyValues| | |
|submitTransaction| | |
|getJoinableSpaces| | |
|deleteBlocks| | |
|getPublicPageData| | |
|deleteSpace| | |
|getUploadFileUrl| | |
|sendEmailVerification| | |
|getActivityLog| | |
|sendTemporaryPassword| | |
|addWebClipperFile| | |
|exportBlock| | |
|exportCollection| | |
|cancelSubscription| | |
|getSubscriptionData| | |
|updateSubscription| | |
|searchBlocks| | |
|searchPages| | |
|searchPagesWithParent| | |
|searchCollections| | |
|searchTrashPages| | |
|duplicateBlock| | |
|  |  |  |


> 下面这些是不想实现的接口

| 连接  | 描述  | 备注  |
|---|---|---|
|getAssetsJson| | |
|getAsanaWorkspaces| | |
|getTrelloBoards| | |
|findUser| | |
|searchGoogleContacts| | |
|createEmailUser| | |
|sendMobileAppLink| | |
|getDesktopDownloadUrl| | |
|importFile| | |
|importTrello| | |
|importAsana| | |
|importEvernote| | |
|enqueueTask| | |
|loginWithEmail| | |
|loginWithGoogleJwt| | |
|loginWithGoogleAuth| | |
|logout| | |
|isGoogleAppsEmail| | |
|disconnectAsana| | |
|disconnectTrello| | |
|disconnectEvernote| | |
|disconnectDrive| | |
|changeEmail| | |
|deleteUser| | |
|adminDeleteUser| | |
|getInvoiceData| | |
|addUsersToSpace| | |
|removeUsersFromSpace| | |
|getUserAnalyticsSettings| | |
|| | |
|setPageNotificationsAsReadAndVisited| | |
|setSpaceInviteNotificationsAsReadAndVisited| | |
|unsubscribeFromEmails| | |
|initializeUserAnalytics| | |
|tSignedFileUrls| | |
|getBillingHistory| | |
|transferPermissionGroup| | |
|disableUserAnalytics| | |
|createReferral| | |
|unsubscribeFromIntercomEmails| | |
|getSnapshotsList| | |
|getSnapshot| | |
|restoreSnapshot| | |
|sendReferralEmail| | |
|activateReferral| | |
|authWithAsana| | |
|getConnectedAppsStatus| | |
|authWithTrello| | |
|authWithSlack| | |
|authWithGoogleForDrive| | |
|getGoogleDriveAccounts| | |
|initializeGoogleDriveBlock| | |
|refreshGoogleDriveBlock| | |
|getEvernoteNotebooks| | |
|searchWebClipperPages| | |
|authWithEvernote| | |
|recoverUser| | |
|recoverSpace| | |
|revokeUserTokens| | |
|adminSearchUsers| | |
|adminFindDeletedUser| | |
|adminWhitelistCreditDomain| | |
|searchUnsplashImages| | |
|adminSearchSpaces| | |
|getUserTasks| | |
|getTasks | | |