package ru.itis.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.exceptions.ExceptionEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionDto {
    private List<ExceptionEntity> exceptions;
}
