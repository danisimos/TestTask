package ru.itis.dto.mappers;

import org.mapstruct.Mapper;
import ru.itis.dto.UrlDto;
import ru.itis.models.Url;

@Mapper
public abstract class UrlMapper {
    public abstract UrlDto toUrlDto(Url url);
}
