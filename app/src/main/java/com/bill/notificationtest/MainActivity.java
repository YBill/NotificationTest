package com.bill.notificationtest;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bill.module_notification.ImportanceType;
import com.bill.module_notification.NotifyManager;

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
        notifyManager.createNotificationChannel(Constants.CHANNEL_CHAT, "聊天消息", ImportanceType.IMPORTANCE_HIGH);
        notifyManager.createNotificationChannel(Constants.CHANNEL_SUBSCRIBE, "订阅消息", ImportanceType.IMPORTANCE_DEFAULT);
    }

    public void handleChat(View view) {
        NotificationCompat.Builder builder = notifyManager.getDefaultBuilder(Constants.CHANNEL_CHAT);
        builder.setContentTitle("收到了Bill发来的消息");
        builder.setContentText("今天晚上需要加班吗？");
        Notification notification = builder.build();
        notifyManager.notifyNotify(notification);
    }

    public void handleSubscribe(View view) {
        if (!notifyManager.areNotificationsEnabled()) {
            Toast.makeText(this, "总通知被关闭", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!notifyManager.areChannelsEnabled(Constants.CHANNEL_SUBSCRIBE)) {
                Toast.makeText(getApplicationContext(), "当前渠道通知被关闭", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        NotificationCompat.Builder builder = notifyManager.getDefaultBuilder(Constants.CHANNEL_SUBSCRIBE);
        builder.setContentTitle("订阅号通知");
        builder.setContentText("您关注的公众号有新的订阅消息");
        Notification notification = builder.build();
        notifyManager.notifyNotify(notification);
    }
}
