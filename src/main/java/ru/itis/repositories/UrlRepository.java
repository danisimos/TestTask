package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.Url;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortUrl(String shortUrl);
    boolean existsByShortUrl(String shortUrl);
    void deleteByExpiredAtBefore(LocalDateTime localDateTime);
}
