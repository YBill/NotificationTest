package com.bill.notificationtest;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bill.module_notification.ChannelEntity;
import com.bill.module_notification.ImportanceType;
import com.bill.module_notification.NotifyManager;

import java.util.ArrayList;

/**
 * Created by Bill on 2018/10/22.
 * <p> Android O 通知栏适配
 * 参考文章：https://blog.csdn.net/guolin_blog/article/details/79854070#commentsedit
 */
public class MainActivity extends AppCompatActivity {

    private NotifyManager notifyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notifyManager = new NotifyManager(this);

        ChannelEntity chatChannel = new ChannelEntity(Constants.CHANNEL_CHAT, "新聊天消息", ImportanceType.IMPORTANCE_HIGH);
        chatChannel.setDescription("个人或群组发来的聊天消息");
        notifyManager.createNotificationGroupWithChannel(Constants.GROUP_CHAT, "聊天消息", chatChannel);

        ArrayList<ChannelEntity> channelEntityArrayList = new ArrayList<>();
        ChannelEntity downloadCompleteChannel = new ChannelEntity(Constants.CHANNEL_DOWNLOAD_COMPLETE, "下载完成", ImportanceType.IMPORTANCE_LOW);
        downloadCompleteChannel.setDescription("下载完成后通知栏显示");
        channelEntityArrayList.add(downloadCompleteChannel);
        ChannelEntity downloadProgressChannel = new ChannelEntity(Constants.CHANNEL_DOWNLOAD_ERROR, "下载失败", ImportanceType.IMPORTANCE_DEFAULT);
        downloadProgressChannel.setDescription("下载出现问题，下载失败");
        channelEntityArrayList.add(downloadProgressChannel);
        notifyManager.createNotificationGroupWithChannel(Constants.GROUP_DOWNLOAD, "下载消息", channelEntityArrayList);

        notifyManager.createNotificationChannel(Constants.CHANNEL_OTHER, "未分类", ImportanceType.IMPORTANCE_MIN, null);
    }

    public void handleChat(View view) {
        if (!notifyManager.areNotificationsEnabled()) {
            Toast.makeText(this, "总通知被关闭", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!notifyManager.areChannelsEnabled(Constants.CHANNEL_CHAT)) {
                Toast.makeText(getApplicationContext(), "当前渠道通知被关闭", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        NotificationCompat.Builder builder = notifyManager.getDefaultBuilder(Constants.CHANNEL_CHAT);
        builder.setContentTitle("收到了Bill发来的消息");
        builder.setContentText("今天晚上需要加班吗？");
        Notification notification = builder.build();
        notifyManager.notifyNotify(notification);
    }

    public void handleCompete(View view) {
        NotificationCompat.Builder builder = notifyManager.getDefaultBuilder(Constants.CHANNEL_DOWNLOAD_COMPLETE);
        builder.setContentTitle("下载完成");
        builder.setContentText("下载完成，可在我的下载中查看");
        Notification notification = builder.build();
        notifyManager.notifyNotify(notification);
    }

    public void handleError(View view) {
        NotificationCompat.Builder builder = notifyManager.getDefaultBuilder(Constants.CHANNEL_DOWNLOAD_ERROR);
        builder.setContentTitle("下载失败");
        builder.setContentText("由于网络中断导致下载失败");
        Notification notification = builder.build();
        notifyManager.notifyNotify(notification);
    }

    public void handleOther(View view) {
        NotificationCompat.Builder builder = notifyManager.getDefaultBuilder(Constants.CHANNEL_OTHER);
        builder.setContentTitle("其他消息");
        builder.setContentText("系统通知消息");
        Notification notification = builder.build();
        notifyManager.notifyNotify(notification);
    }
}
