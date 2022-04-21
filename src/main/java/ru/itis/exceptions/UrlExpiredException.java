package ru.itis.exceptions;

public class UrlExpiredException extends RuntimeException{
    public UrlExpiredException(String message) {
        super(message);
    }
}
