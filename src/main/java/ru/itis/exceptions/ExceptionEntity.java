package ru.itis.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExceptionEntity {
    public ExceptionEntity(Exception e) {
        this.object = e.getMessage();
    }

    private String object;
    private String message;
}
