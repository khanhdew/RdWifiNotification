package com.khanhdew.rd_wifi_noti.services;

import com.khanhdew.rd_wifi_noti.exceptions.TokenNotFoundException;
import com.khanhdew.rd_wifi_noti.models.FirebaseToken;

import java.util.List;

public interface FirebaseTokenService {
    public FirebaseToken saveToken(FirebaseToken firebaseToken);
    public FirebaseToken updateToken(String token, int notiPermission) throws TokenNotFoundException;
    public void deleteToken(String token) throws TokenNotFoundException;
    public List<FirebaseToken> getTokensByUserId(Long userId);
    public List<FirebaseToken> getAllTokens();
    public List<String> getAllTokensToString(List<FirebaseToken> firebaseTokens);
    public Long getNumberOfDevices();
}
