package com.khanhdew.rd_wifi_noti.controllers;

import com.google.firebase.messaging.*;
import com.khanhdew.rd_wifi_noti.exceptions.TokenNotFoundException;
import com.khanhdew.rd_wifi_noti.models.FirebaseNotification;
import com.khanhdew.rd_wifi_noti.models.FirebaseToken;
import com.khanhdew.rd_wifi_noti.models.FirebaseNotificationMultiRecipents;
import com.khanhdew.rd_wifi_noti.services.FirebaseTokenService;
import com.khanhdew.rd_wifi_noti.services.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class NotificationController {
    private final FirebaseTokenService firebaseTokenService;
    private final NotificationService notificationService;
    private final FirebaseMessaging firebaseMessaging;

    @PostMapping("/notification/create-firebase-token")
    public ResponseEntity<FirebaseToken> createFirebaseToken(@RequestBody FirebaseToken firebaseToken) {
        firebaseTokenService.saveToken(firebaseToken);
        return new ResponseEntity<>(firebaseToken, HttpStatus.CREATED);
    }

    @PutMapping("/notification/update-firebase-token")
    public ResponseEntity<FirebaseToken> updateFirebaseToken(@RequestParam String token, int notiPermission) {
        FirebaseToken firebaseToken = null;
        try {
            firebaseToken = firebaseTokenService.updateToken(token, notiPermission);
            return new ResponseEntity<>(firebaseToken, HttpStatus.OK);
        } catch (TokenNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/notification/delete-firebase-token")
    public ResponseEntity<Void> deleteFirebaseToken(@RequestParam String token) {
        try {
            firebaseTokenService.deleteToken(token);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TokenNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/notification/{accountId}")
    public ResponseEntity<List<FirebaseNotification>> getAccountNotifications(@PathVariable long accountId) {
        List<FirebaseNotification> firebaseNotifications = notificationService.getAllNotificationsByUserId(accountId);
        return new ResponseEntity<>(firebaseNotifications, HttpStatus.OK);
    }

    @PostMapping("/notification/send-broadcast")
    public ResponseEntity<List<String>> sendBroadcastNotification(@RequestBody FirebaseNotification firebaseNotification) throws FirebaseMessagingException {
        List<String> firebaseTokens = firebaseTokenService.getAllTokensToString(firebaseTokenService.getAllTokens());
        Notification notification = Notification.builder()
                .setTitle(firebaseNotification.getTitle())
                .setImage(firebaseNotification.getImageUrl())
                .setBody(firebaseNotification.getContent())
                .build();
        MulticastMessage msg = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(firebaseTokens)
                .putData("body", firebaseNotification.getContent())
                .build();

        notificationService.saveNotification(firebaseNotification);

        BatchResponse response = firebaseMessaging.sendMulticast(msg);
        List<String> ids = response.getResponses()
                .stream()
                .map(r -> r.getMessageId())
                .collect(Collectors.toList());

        return new ResponseEntity<>(ids, HttpStatus.ACCEPTED);
    }

    @PostMapping("/notification/send-multi/{accountId}")
    public ResponseEntity<List<String>> sendMultiCastToAccount(@PathVariable long accountId, @RequestBody FirebaseNotification firebaseNotification) throws FirebaseMessagingException {
        List<String> firebaseTokens = firebaseTokenService.getAllTokensToString(firebaseTokenService.getTokensByUserId(accountId));
        Notification notification = Notification.builder()
                .setTitle(firebaseNotification.getTitle())
                .setImage(firebaseNotification.getImageUrl())
                .setBody(firebaseNotification.getContent())
                .build();
        MulticastMessage msg = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(firebaseTokens)
                .putData("body", firebaseNotification.getContent())
                .build();

        notificationService.saveNotification(firebaseNotification);

        BatchResponse response = firebaseMessaging.sendMulticast(msg);
        List<String> ids = response.getResponses()
                .stream()
                .map(SendResponse::getMessageId)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ids, HttpStatus.ACCEPTED);
    }

    @PostMapping("/notification/send-multi")
    public ResponseEntity<List<String>> sendMultiCastToMultiAccount(@RequestBody FirebaseNotificationMultiRecipents firebaseNotification) throws FirebaseMessagingException {
        List<String> firebaseTokens = new ArrayList<>();
        var recipientIds = firebaseNotification.getRecipientIds();
        for (Long accountId : recipientIds) {
            firebaseTokens.addAll(firebaseTokenService.getAllTokensToString(firebaseTokenService.getTokensByUserId(accountId)));
        }

        Notification notification = Notification.builder()
                .setTitle(firebaseNotification.getTitle())
                .setImage(firebaseNotification.getImageUrl())
                .setBody(firebaseNotification.getContent())
                .build();
        MulticastMessage msg = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(firebaseTokens)
                .putData("body", firebaseNotification.getContent())
                .build();

        for (Long accountId : recipientIds) {
            notificationService.saveNotification(new FirebaseNotification(firebaseNotification.getTitle(),
                    firebaseNotification.getContent(),
                    firebaseNotification.getSenderId(),
                    firebaseNotification.getDeviceAttributeId(),
                    firebaseNotification.getDeviceId(),
                    accountId,
                    firebaseNotification.getUnRead(),
                    firebaseNotification.getTime(),
                    firebaseNotification.getLink(),
                    firebaseNotification.getDormitoryId(),
                    firebaseNotification.getHomeControllerId(),
                    firebaseNotification.getImageUrl(),
                    firebaseNotification.getNotificationTypeId()));
        }
        BatchResponse response = firebaseMessaging.sendMulticast(msg);
        List<String> ids = response.getResponses()
                .stream()
                .map(SendResponse::getMessageId)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ids, HttpStatus.ACCEPTED);
    }

    @PostMapping("/notification/send-uni/{token}")
    public ResponseEntity<String> sendUniCast(@PathVariable String token,@RequestBody FirebaseNotification firebaseNotification) throws FirebaseMessagingException {
        if(firebaseTokenService.getFirebaseTokenByToken(token)==null){
            log.error("Token {} not found", token);
            throw new TokenNotFoundException("Token not found");
        }

        Notification notification = Notification.builder()
                .setTitle("Có người đã bấm chuông cửa")
                .setBody(firebaseNotification.getContent())
                .setImage(firebaseNotification.getImageUrl())
                .build();

        Message msg = Message.builder()
                .setToken(token)
                .putData("Có người bấm chuông cửa vào lúc ", firebaseNotification.getContent())
                .setNotification(notification)
                .build();

        String id = firebaseMessaging.send(msg);
        return new ResponseEntity<String>(id, HttpStatus.OK);
    }

}
