package com.khanhdew.rd_wifi_noti.repositories;

import com.khanhdew.rd_wifi_noti.models.FirebaseNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<FirebaseNotification,Long> {
    List<FirebaseNotification> getAllByRecipientId(long recipientId);
}
