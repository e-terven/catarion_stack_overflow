package com.javamentor.qa.platform.security.jwt;

/**
 *  Исключение, которое выбрасывается при ошибке валидации JWT токена
 *
 * @author Alex Zarubin
 * created on 15.11.2022
 */
public class JwtAuthenticationException extends Throwable {

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
