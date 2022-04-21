package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlDto {
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime expiredAt;
}
