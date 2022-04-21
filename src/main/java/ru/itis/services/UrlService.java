package ru.itis.services;

import ru.itis.dto.UrlDto;
import ru.itis.dto.forms.UrlForm;

public interface UrlService {
    UrlDto createShortUrl(UrlForm urlForm);

    UrlDto getByShortUrl(String shortUrl);
}
