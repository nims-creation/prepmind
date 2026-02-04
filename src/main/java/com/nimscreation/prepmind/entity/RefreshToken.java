package com.nimscreation.prepmind.entity;

import com.nimscreation.prepmind.entity.base.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class RefreshToken {
    @Id
    private String token;

    @OneToOne
    private User user;

    private LocalDateTime expiry;
}
