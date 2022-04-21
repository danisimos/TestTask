package ru.itis.dto.forms;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlForm {
    @NotNull(message = "Original url cannot be null")
    @NotBlank(message = "Original url cannot be empty")
    @URL(message = "Wrong url")
    private String originalUrl;

    @Future(message = "Wrong date pattern or expired date is not valid")
    private LocalDateTime expiredAt;

}
