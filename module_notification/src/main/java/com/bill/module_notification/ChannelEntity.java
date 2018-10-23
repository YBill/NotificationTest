package com.bill.module_notification;

import android.support.annotation.NonNull;

public class ChannelEntity {

    private String channelId;
    private String channelName;
    private int importance;
    private String description;

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
}
