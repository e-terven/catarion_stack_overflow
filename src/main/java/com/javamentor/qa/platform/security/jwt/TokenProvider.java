package com.javamentor.qa.platform.security.jwt;

import com.javamentor.qa.platform.service.impl.model.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {

    private final UserServiceImpl userService;


    @Value("${registration.verification.expiration}")
    private int EXPIRATION_TIME;

    public TokenProvider(UserServiceImpl userService) {
        this.userService = userService;
    }

    public String generateToken(String email) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = new SecretKeySpec(String.valueOf(email.hashCode()).getBytes() ,signatureAlgorithm.getJcaName());
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiration = new Date(nowMillis + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(signatureAlgorithm, signingKey)
                .compact();
    }

    public boolean validateToken(String token, String email) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            Key signingKey = new SecretKeySpec(String.valueOf(email.hashCode()).getBytes() ,signatureAlgorithm.getJcaName());
            Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .setAllowedClockSkewSeconds(60)
                .parseClaimsJws(token).getBody();
            String subject = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date();
            return userService.getByEmail(subject).isPresent() || expiration.after(now);
        } catch (Exception e) {
            return false;
        }
    }


}
