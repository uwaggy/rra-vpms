package com.rra.template.auth;

import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
public class Jwt {
    private final Claims claims;
    private final SecretKey secretKey;
    public boolean isExpired(){
        return claims.getExpiration().before(new Date());
    }
    public UUID getUserId(){
        return U
    }
}
