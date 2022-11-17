package com.javamentor.qa.platform.security.jwt;

import io.jsonwebtoken.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 *  Утилитный класс для работы с JWT токенами
 *
 * @author Alex Zarubin
 * created on 15.11.2022
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secretKey;
    @Value("${jwt.token.expired}")
    private Long timeToExpire;

    private final UserDetailsService userDetailsService;


    public JwtTokenProvider(UserDetailsService userDetailsService) {

        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     *  Создание JWT токена
     *
     * @param login - имя пользователя
     * @param role - роль пользователя
     * @return JWT токен
     */
    public String createToken(String login, String role) {

        Claims claims = Jwts.claims().setSubject(login);
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + timeToExpire);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     *  Получение аутентификации из JWT токена
     *
     * @param token - JWT токен
     * @return аутентификация
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getLogin(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     *  Получение токена из запроса
     *
     * @param req - запрос
     * @return JWT токен
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    /**
     *  Проверка валидности JWT токена
     *
     * @param token - JWT токен
     * @return true - если токен валидный, false - если нет
     */
    @SneakyThrows
    public boolean validateToken(String token) {

        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    /**
     *  Получение имени пользователя из JWT токена
     *
     * @param token - JWT токен
     * @return имя пользователя
     */
    private String getLogin(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
