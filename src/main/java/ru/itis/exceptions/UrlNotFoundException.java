package ru.itis.exceptions;

public class UrlNotFoundException extends RuntimeException{
    public UrlNotFoundException(String message) {
        super(message);
    }
}
