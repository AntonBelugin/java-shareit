package ru.practicum.shareit.request;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.RequestWithItemDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@SpringBootTest(
        properties = "jdbc.url=jdbc:postgresql://localhost:5432/test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestServiceTest {
    private final EntityManager em;
    private final RequestService service;

    User user;
    Request request;
    Long userId;

    @BeforeEach
    void setUp() {
        String userName = "userName";
        String userEmail = "test@mail.ru";
        userId = 1L;
        LocalDateTime created = LocalDateTime.now();
        user = User.builder()
                .name(userName)
                .email(userEmail)
                .build();
        em.persist(user);

        String description = "description";
        request = Request.builder()
                .description(description)
                .searcher(user)
                .created(created)
                .build();
        em.persist(request);
    }

    @Test
    void getRequestByIdTest() {
        RequestWithItemDto requestWithItemDto = service.getRequestById(1L);

        assertThat(request.getDescription(), equalTo((requestWithItemDto.getDescription())));
        assertThat(request.getCreated(), equalTo(requestWithItemDto.getCreated()));
    }
}
