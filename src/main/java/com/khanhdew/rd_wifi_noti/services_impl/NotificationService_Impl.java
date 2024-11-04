package com.khanhdew.rd_wifi_noti.services_impl;

import com.khanhdew.rd_wifi_noti.models.FirebaseNotification;
import com.khanhdew.rd_wifi_noti.models.FirebaseNotificationMultiRecipents;
import com.khanhdew.rd_wifi_noti.repositories.NotificationRepository;
import com.khanhdew.rd_wifi_noti.services.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService_Impl implements NotificationService {

    private NotificationRepository notificationRepository;

    @Override
    public List<FirebaseNotification> getAllNotificationsByUserId(long userId) {
        return notificationRepository.getAllByRecipientId(userId);
    }

    @Override
    public FirebaseNotification saveNotification(FirebaseNotification firebaseNotification) {
        return notificationRepository.save(firebaseNotification);
    }

    @Override
    public List<FirebaseNotification> saveNotification(List<FirebaseNotification> firebaseNotifications) {
        return notificationRepository.saveAll(firebaseNotifications);
    }
}
