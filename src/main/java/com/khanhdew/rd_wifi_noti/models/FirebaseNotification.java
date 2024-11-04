package com.khanhdew.rd_wifi_noti.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirebaseNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String deviceId;
    private Long deviceAttributeId;
    private Long senderId;
    private Long recipientId;
    private Boolean unRead;
    private Date time;
    private String link;
    private String dormitoryId;
    private String homeControllerId;
    private String imageUrl;
    private Long notificationTypeId;

    public FirebaseNotification(String title, String content, Long senderId, Long deviceAttributeId, String deviceId, Long recipientId, Boolean unRead, Date time, String link, String dormitoryId, String homeControllerId, String imageUrl, Long notificationTypeId) {
        this.title = title;
        this.content = content;
        this.senderId = senderId;
        this.deviceAttributeId = deviceAttributeId;
        this.deviceId = deviceId;
        this.recipientId = recipientId;
        this.unRead = unRead;
        this.time = time;
        this.link = link;
        this.dormitoryId = dormitoryId;
        this.homeControllerId = homeControllerId;
        this.imageUrl = imageUrl;
        this.notificationTypeId = notificationTypeId;
    }
}
