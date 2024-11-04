package com.khanhdew.rd_wifi_noti.initr;

import com.khanhdew.rd_wifi_noti.models.FirebaseToken;
import com.khanhdew.rd_wifi_noti.services.FirebaseTokenService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private FirebaseTokenService firebaseTokenService;

    @Override
    public void run(String... args) throws Exception {
        if(firebaseTokenService.getNumberOfDevices() > 1){
            System.out.println("Already init");
            return;
        }
        List<FirebaseToken> u1Tokens = Arrays.asList(
                new FirebaseToken("ABCSKALSDKASDA",1),
                new FirebaseToken("LKSDFJLDSJLKFA",3),
                new FirebaseToken("OIWEJAWMALKMSL",2)
        );

        List<FirebaseToken> u2Tokens = Arrays.asList(
                new FirebaseToken("YHDASJNDKNAZCA",1),
                new FirebaseToken("LKASJDPASJDAPS",3),
                new FirebaseToken("YIUWGEBJKAMN",2)
        );

        u1Tokens.stream().forEach(a -> firebaseTokenService.saveToken(a));
        u2Tokens.stream().forEach(a -> firebaseTokenService.saveToken(a));

    }
}
