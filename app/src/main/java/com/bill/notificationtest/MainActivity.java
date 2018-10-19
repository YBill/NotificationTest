package com.bill.notificationtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * Create By Bill
 * <p> Android O 通知栏适配
 * 参考郭神：https://blog.csdn.net/guolin_blog/article/details/79854070#commentsedit
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    public void handleChat(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "chat")
                .setContentTitle("收到一条聊天消息")
                .setContentText("今天中午吃什么？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon_small_push2)
                .setColor(Color.parseColor("#E92110"))
                .setAutoCancel(true)
                .setNumber(10)
                .build();
        int notifyId = new Random().nextInt(50000);
        manager.notify(notifyId, notification);
    }

    public void handleSubscribe(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = manager.getNotificationChannel("subscribe");
            if (notificationChannel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Notification notification = new NotificationCompat.Builder(this, "subscribe")
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon_small_push2)
                .setColor(Color.parseColor("#E92110"))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_push))
                .setAutoCancel(true)
                .setNumber(3)
                .build();
        int notifyId = new Random().nextInt(50000);
        manager.notify(notifyId, notification);
    }
}
