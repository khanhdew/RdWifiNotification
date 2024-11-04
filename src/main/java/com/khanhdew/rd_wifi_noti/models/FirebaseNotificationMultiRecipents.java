package com.khanhdew.rd_wifi_noti.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirebaseNotificationMultiRecipents {
    private String title;
    private String content;
    private String deviceId;
    private Long deviceAttributeId;
    private Long senderId;
    private List<Long> recipientIds;
    private Boolean unRead;
    private Date time;
    private String link;
    private String dormitoryId;
    private String homeControllerId;
    private String imageUrl;
    private Long notificationTypeId;
}