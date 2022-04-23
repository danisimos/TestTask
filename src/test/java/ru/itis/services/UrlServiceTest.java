package ru.itis.services;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import ru.itis.dto.UrlDto;
import ru.itis.dto.forms.UrlForm;
import ru.itis.dto.mappers.UrlMapper;
import ru.itis.exceptions.UrlNotFoundException;
import ru.itis.models.Url;
import ru.itis.repositories.UrlRepository;
import ru.itis.services.impl.UrlServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DisplayName("UrlService working")
@ActiveProfiles("dev")
public class UrlServiceTest {
    @MockBean
    private UrlRepository urlRepository;
    @MockBean
    private UrlMapper urlMapper;

    private UrlServiceImpl urlService;

    @BeforeEach
    public void setUp() {
        when(urlRepository.findByShortUrl("abcdabcd"))
                .thenReturn(Optional.of(Url.builder()
                        .originalUrl("https://www.google.ru/")
                        .expiredAt(LocalDateTime.parse("2022-04-30T14:18:13.5999632"))
                        .shortUrl("abcdabcd")
                        .id(1L)
                        .build()));

        when(urlMapper.toUrlDto(Url.builder()
                .originalUrl("https://www.google.ru/")
                .expiredAt(LocalDateTime.parse("2022-04-30T14:18:13.5999632"))
                .shortUrl("abcdabcd")
                .id(1L)
                .build()))
                .thenReturn(UrlDto.builder()
                        .id(1L)
                        .originalUrl("https://www.google.ru/")
                        .expiredAt(LocalDateTime.parse("2022-04-30T14:18:13.5999632"))
                        .shortUrl("abcdabcd")
                        .build());

        urlService = new UrlServiceImpl(urlRepository, urlMapper);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getByShortUrl() working")
    public class createShortUrlTest {
        @Test
        public void return_urldto() throws Exception {
            UrlDto urlDto = urlService.getByShortUrl("abcdabcd");

            assertThat(urlDto.getShortUrl(), is("abcdabcd"));
            assertThat(urlDto.getId(), is(1L));
        }
    }
}
