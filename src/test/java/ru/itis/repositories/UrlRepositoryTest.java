package ru.itis.repositories;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.models.Url;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@DataJpaTest
public class UrlRepositoryTest {
    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void save() {
        Url url = urlRepository.save(Url.builder()
                .shortUrl("short")
                .build());

        assertThat(url.getShortUrl(), is("short"));
    }
}
