package ru.itis.controllers;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.dto.UrlDto;
import ru.itis.dto.forms.UrlForm;
import ru.itis.exceptions.UrlNotFoundException;
import ru.itis.services.UrlService;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@DisplayName("UrlController working")
public class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;

    @BeforeEach
    public void setUp() {
        when(urlService.createShortUrl(UrlForm.builder()
                .originalUrl("https://www.google.ru/")
                .expiredAt(LocalDateTime.parse("2022-04-30T14:18:13.5999632"))
                .build()))
                .thenReturn(UrlDto.builder()
                        .id(1L)
                        .originalUrl("https://www.google.ru/")
                        .expiredAt(LocalDateTime.parse("2022-04-30T14:18:13.5999632"))
                        .shortUrl("2db3cdb7")
                        .build());

        when(urlService.getByShortUrl("2db3cdb7"))
                .thenReturn(UrlDto.builder()
                        .id(1L)
                        .originalUrl("https://www.google.ru/")
                        .expiredAt(LocalDateTime.parse("2022-04-30T14:18:13.5999632"))
                        .shortUrl("2db3cdb7")
                        .build());

        when(urlService.getByShortUrl("aaaaaaaa"))
                .thenThrow(UrlNotFoundException.class);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("createShortUrl() working")
    public class createShortUrlTest {
        @Test
        public void return_urldto() throws Exception {
            mockMvc.perform(post("/api/createShortUrl")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "  \"originalUrl\": \"https://www.google.ru/\",\n" +
                            "  \"expiredAt\": \"2022-04-30T14:18:13.5999632\"\n" +
                            "}"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)));
        }

        @Test
        public void already_past_expired_date() throws Exception {
            mockMvc.perform(post("/api/createShortUrl")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "  \"originalUrl\": \"https://www.google.ru/\",\n" +
                            "  \"expiredAt\": \"2022-03-30T14:18:13.5999632\"\n" +
                            "}"))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        public void wrong_url_format() throws Exception {
            mockMvc.perform(post("/api/createShortUrl")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "  \"originalUrl\": \"httpf//wwwgoogle.ru/\",\n" +
                            "  \"expiredAt\": \"2022-04-30T14:18:13.5999632\"\n" +
                            "}"))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getByShortUrl() working")
    public class getByShortUrlTest {
        @Test
        public void return_urldto() throws Exception {
            mockMvc.perform(get("/api/getByShortUrl/2db3cdb7"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)));
        }

        @Test
        public void short_url_not_found() throws Exception {
            mockMvc.perform(get("/api/getByShortUrl/aaaaaaaa"))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("redirect() working")
    public class redirectTest {
        @Test
        public void return_found() throws Exception {
            mockMvc.perform(get("/api/redirect/2db3cdb7"))
                    .andDo(print())
                    .andExpect(status().isFound());
        }
    }
}
