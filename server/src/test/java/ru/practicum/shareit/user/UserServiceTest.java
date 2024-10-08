package ru.practicum.shareit.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.model.User;

@Transactional
@SpringBootTest(
        properties = "jdbc.url=jdbc:postgresql://localhost:5432/test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {
    private final EntityManager em;
    private final UserService service;

    Long userId;
    User user;

    @BeforeEach
    void setUp() {
        String userName = "userName";
        String userEmail = "test@mail.ru";
        userId = 1L;
        user = User.builder()
                .name(userName)
                .email(userEmail)
                .build();
        em.persist(user);
    }

  /*  @Test
    void findByIdTest() {
        UserDto userDto = service.findById(userId);

        assertThat(user.getName(), equalTo((userDto.getName())));
        assertThat(user.getEmail(), equalTo((userDto.getEmail())));
    }*/
}
