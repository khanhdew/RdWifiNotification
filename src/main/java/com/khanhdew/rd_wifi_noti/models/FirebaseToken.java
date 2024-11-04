package com.khanhdew.rd_wifi_noti.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirebaseToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    public FirebaseToken(String token, int notiPermission){
        this.token = token;
        this.notiPermission = notiPermission;
    }

    private String token;

    private int notiPermission;

}
