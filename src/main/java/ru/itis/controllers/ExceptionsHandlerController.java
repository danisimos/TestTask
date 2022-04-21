package ru.itis.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itis.exceptions.ExceptionDto;
import ru.itis.exceptions.ExceptionEntity;
import ru.itis.exceptions.UrlExpiredException;
import ru.itis.exceptions.UrlNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionsHandlerController {
    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handle(MethodArgumentNotValidException exception) {
        List<ExceptionEntity> exceptions = exception.getAllErrors().stream()
                .map(objectError -> new ExceptionEntity(objectError.getObjectName(), objectError.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(exceptions));
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotUniqueShortUrlException(UrlNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(Collections.singletonList(new ExceptionEntity(exception))));
    }

    @ExceptionHandler(UrlExpiredException.class)
    public ResponseEntity<ExceptionDto> handleUrlExpiredException(UrlExpiredException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(Collections.singletonList(new ExceptionEntity(exception))));
    }
}
