package com.bill.module_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

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

    public void init() {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        random = new Random();
    }

    /**
     * 创建渠道
     *
     * @param channelId
     * @param channelName
     * @param importance
     */
    public void createNotificationChannel(String channelId, String channelName, @ImportanceType int importance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(channel);
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
        builder.setSmallIcon(R.drawable.icon_small_push2)
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
     * Generate a random integer
     *
     * @return int, [0, 50000)
     */
    private int getRandomId() {
        return random.nextInt(50000);
    }

}
