package com.khanhdew.rd_wifi_noti.repositories;

import com.khanhdew.rd_wifi_noti.models.FirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirebaseTokenRepository extends JpaRepository<FirebaseToken, Long> {
    List<FirebaseToken> getAllByUserId(long userId);
    FirebaseToken getByToken(String token);
}
