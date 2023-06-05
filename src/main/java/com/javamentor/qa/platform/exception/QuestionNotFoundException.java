package com.javamentor.qa.platform.exception;

/**Ошибка отсутствия ответа в БД.*/
public class QuestionNotFoundException extends RuntimeException{
    public QuestionNotFoundException() {
    }
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
