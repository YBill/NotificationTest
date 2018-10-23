package com.bill.module_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Bill on 2018/10/22.
 * 通知管理类
 */

public class NotifyManager {

    private Context context;
    private NotificationManager notificationManager;
    private Random random;

    public NotifyManager(Context context) {
        this.context = context.getApplicationContext();
        init();
    }

    private void init() {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        random = new Random();
    }

    /**
     * 创建渠道
     *
     * @param channelId   渠道Id
     * @param channelName 渠道名
     * @param importance  等级
     * @param description 渠道描述
     */
    public void createNotificationChannel(@NonNull String channelId, @NonNull String channelName, @ImportanceType int importance, @Nullable String description) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelId, channelName, importance, description, null);
        }
    }

    /**
     * 创建渠道组和一个渠道
     *
     * @param groupId
     * @param groupName
     * @param channel
     */
    public void createNotificationGroupWithChannel(String groupId, String groupName, ChannelEntity channel) {
        ArrayList<ChannelEntity> channelList = new ArrayList<>();
        channelList.add(channel);
        createNotificationGroupWithChannel(groupId, groupName, channelList);
    }

    /**
     * 创建渠道组和一组渠道
     *
     * @param groupId
     * @param groupName
     * @param channelList
     */
    public void createNotificationGroupWithChannel(String groupId, String groupName, ArrayList<ChannelEntity> channelList) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (!TextUtils.isEmpty(groupId)) {
                createNotificationGroup(groupId, groupName);
            }

            for (ChannelEntity channel : channelList) {
                createNotificationChannel(channel.getChannelId(), channel.getChannelName(), channel.getImportance(), channel.getDescription(), groupId);
            }
        }
    }

    /**
     * 创建渠道，并创建组
     *
     * @param channelId
     * @param channelName
     * @param importance
     * @param description
     * @param groupId
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(@NonNull String channelId, @NonNull String channelName, @ImportanceType int importance,
                                           @Nullable String description, @Nullable String groupId) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        if (!TextUtils.isEmpty(description))
            channel.setDescription(description);
        if (!TextUtils.isEmpty(groupId))
            channel.setGroup(groupId);
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * 创建渠道组
     *
     * @param groupId
     * @param groupName
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationGroup(String groupId, String groupName) {
        NotificationChannelGroup group = new NotificationChannelGroup(groupId, groupName);
        notificationManager.createNotificationChannelGroup(group);
    }

    /**
     * 删除渠道
     *
     * @param channelId
     */
    public void deleteNotificationChannel(@NonNull String channelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.deleteNotificationChannel(channelId);
        }
    }

    /**
     * 删除组
     *
     * @param groupId
     */
    public void deleteNotificationChannelGroup(@NonNull String groupId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.deleteNotificationChannelGroup(groupId);
        }
    }

    /**
     * 发送通知
     *
     * @param notification 通知具体内容
     * @return 通知Id
     */
    public int notifyNotify(Notification notification) {
        int notifyId = getRandomId();
        return notifyNotify(notifyId, notification);
    }

    /**
     * 发送通知
     *
     * @param notifyId     通知Id
     * @param notification 通知具体内容
     * @return
     */
    public int notifyNotify(int notifyId, Notification notification) {
        notificationManager.notify(notifyId, notification);
        return notifyId;
    }

    /**
     * 关闭状态栏通知的显示
     *
     * @param notifyId 通知Id
     */
    public void cancelNotify(int notifyId) {
        notificationManager.cancel(notifyId);
    }

    /**
     * 默认设置，调用方可以添加和修改
     *
     * @return NotificationCompat.Builder
     */
    public NotificationCompat.Builder getDefaultBuilder(String channelId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setSmallIcon(R.drawable.push)
                .setColor(Color.parseColor("#E92110"));
        return builder;
    }

    /**
     * 检查当前渠道的通知是否可用，Android O及以上版本调用
     * <p>
     * 注：areNotificationsEnabled()返回false时，即当前App通知被关时，此方法仍可能返回true，
     *
     * @param channelId 渠道Id
     * @return false：不可用
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean areChannelsEnabled(String channelId) {
        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);
        if (notificationChannel != null && notificationChannel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
            return false;
        }
        return true;
    }

    /**
     * 检查通知是否可用
     *
     * @return false：不可用
     */
    public boolean areNotificationsEnabled() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        return notificationManagerCompat.areNotificationsEnabled();
    }

    /**
     * 调转到渠道设置页
     *
     * @param channelId
     */
    public void gotoChannelSetting(@NonNull String channelId) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
        context.startActivity(intent);
    }

    /**
     * Generate a random integer
     *
     * @return int, [0, 50000)
     */
    private int getRandomId() {
        return random.nextInt(50000);
    }

}
