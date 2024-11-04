package com.khanhdew.rd_wifi_noti.services;

import com.khanhdew.rd_wifi_noti.models.FirebaseNotification;

import java.util.List;

public interface NotificationService {
    public List<FirebaseNotification> getAllNotificationsByUserId(long userId);
    public FirebaseNotification saveNotification(FirebaseNotification firebaseNotification);
    public List<FirebaseNotification> saveNotification(List<FirebaseNotification> firebaseNotifications);
}
