package ru.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.UrlDto;
import ru.itis.dto.forms.UrlForm;
import ru.itis.services.UrlService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/createShortUrl")
    public ResponseEntity<UrlDto> createShortUrl(@Valid @RequestBody UrlForm urlForm) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(urlService.createShortUrl(urlForm));
    }

    @GetMapping("/getByShortUrl/{shortUrl}")
    public ResponseEntity<UrlDto> getByShortUrl(@PathVariable(name = "shortUrl") String shortUrl) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(urlService.getByShortUrl(shortUrl));
    }

    @GetMapping("/redirect/{shortUrl}")
    public ResponseEntity<?> redirect(@PathVariable(name = "shortUrl") String shortUrl) {
        UrlDto urlDto = urlService.getByShortUrl(shortUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(urlDto.getOriginalUrl()));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
