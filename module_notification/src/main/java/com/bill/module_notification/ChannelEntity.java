package com.bill.module_notification;

import android.support.annotation.NonNull;

public class ChannelEntity {

    private String channelId; // 渠道Id
    private String channelName; // 渠道名称
    private int importance; // 重要等级
    private String description; // 描述
    private boolean showBadge = true; // 是否显示icon角标

    public ChannelEntity(@NonNull String channelId, @NonNull String channelName, @ImportanceType int importance) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.importance = importance;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public int getImportance() {
        return importance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShowBadge() {
        return showBadge;
    }

    public void setShowBadge(boolean showBadge) {
        this.showBadge = showBadge;
    }
}
