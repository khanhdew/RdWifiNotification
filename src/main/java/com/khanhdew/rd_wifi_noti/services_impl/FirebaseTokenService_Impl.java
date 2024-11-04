package com.khanhdew.rd_wifi_noti.services_impl;

import com.khanhdew.rd_wifi_noti.exceptions.TokenNotFoundException;
import com.khanhdew.rd_wifi_noti.models.FirebaseToken;
import com.khanhdew.rd_wifi_noti.repositories.FirebaseTokenRepository;
import com.khanhdew.rd_wifi_noti.services.FirebaseTokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FirebaseTokenService_Impl implements FirebaseTokenService {
    private FirebaseTokenRepository firebaseTokenRepository;

    @Transactional
    @Override
    public FirebaseToken saveToken(FirebaseToken firebaseToken) {
        try {
            firebaseTokenRepository.save(firebaseToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("Token {} is saved", firebaseToken.getToken());
        return firebaseToken;
    }

    @Transactional
    @Override
    public FirebaseToken updateToken(String token, int notiPermission) throws TokenNotFoundException{
        FirebaseToken firebaseToken = firebaseTokenRepository.getByToken(token);
        if(firebaseToken == null){
            throw new TokenNotFoundException("Token not found: " + token);
        }
        try {
            firebaseToken.setNotiPermission(notiPermission);
            firebaseTokenRepository.save(firebaseToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("Token {} is updated", firebaseToken.getToken());
        return firebaseToken;
    }


    @Override
    public void deleteToken(String token) throws TokenNotFoundException {
        FirebaseToken firebaseToken = firebaseTokenRepository.getByToken(token);
        if (firebaseToken == null) {
            throw new TokenNotFoundException("Token not found: " + token);
        }
        try {
            firebaseTokenRepository.delete(firebaseToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("Token {} is deleted", firebaseToken.getToken());
    }

    @Override
    public List<FirebaseToken> getTokensByUserId(Long userId) {
        return firebaseTokenRepository.getAllByUserId(userId);
    }

    @Override
    public List<FirebaseToken> getAllTokens() {
        return firebaseTokenRepository.findAll();
    }

    @Override
    public List<String> getAllTokensToString(List<FirebaseToken> firebaseTokens) {
        List<String> tokenToString = new ArrayList<>();
        for(FirebaseToken firebaseToken :  firebaseTokens){
            tokenToString.add(firebaseToken.getToken());
        }
        return tokenToString;
    }

    @Override
    public Long getNumberOfDevices() {
        return firebaseTokenRepository.count();
    }
}
