package ru.itis.services.impl;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.UrlDto;
import ru.itis.dto.forms.UrlForm;
import ru.itis.dto.mappers.UrlMapper;
import ru.itis.exceptions.UrlExpiredException;
import ru.itis.exceptions.UrlNotFoundException;
import ru.itis.models.Url;
import ru.itis.repositories.UrlRepository;
import ru.itis.services.UrlService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;
    private final UrlMapper urlMapper;

    @Scheduled(cron = "0 0/15 * * * *")
    @Transactional
    public void clearExpiredUrl() {
        urlRepository.deleteByExpiredAtBefore(LocalDateTime.now());
    }

    @Override
    public UrlDto createShortUrl(UrlForm urlForm) {
        String shortUrl = generateShortUrlByOriginalUrl(urlForm.getOriginalUrl());

        Url url = Url.builder()
                .originalUrl(urlForm.getOriginalUrl())
                .shortUrl(shortUrl)
                .expiredAt(urlForm.getExpiredAt() == null ? LocalDateTime.now().plusMinutes(2): urlForm.getExpiredAt())
                .build();

        try {
            return urlMapper.toUrlDto(urlRepository.save(url));
        } catch(DataIntegrityViolationException exception) { //shortUrl unique constraint
            shortUrl = generateShortUrlByOriginalUrl(urlForm.getOriginalUrl());

            while(urlRepository.existsByShortUrl(shortUrl)) {
                shortUrl = generateShortUrlByOriginalUrl(urlForm.getOriginalUrl());
            }

            url.setShortUrl(shortUrl);

            return urlMapper.toUrlDto(urlRepository.save(url));
        }
    }

    private String generateShortUrlByOriginalUrl(String originalUrl) {
        return Hashing.murmur3_32_fixed()
                .hashString(originalUrl.concat(String.valueOf(System.currentTimeMillis())), StandardCharsets.UTF_8).toString();
    }

    @Override
    public UrlDto getByShortUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl).orElseThrow(() -> new UrlNotFoundException("No found with such short url or expired"));

        if(url.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new UrlExpiredException("Such url is expired");
        }

        return urlMapper.toUrlDto(url);
    }
}
