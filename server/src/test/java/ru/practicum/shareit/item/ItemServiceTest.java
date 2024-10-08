package ru.practicum.shareit.item;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.ItemDtoWithComments;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@SpringBootTest(
        properties = "jdbc.url=jdbc:postgresql://localhost:5432/test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceTest {
    private final EntityManager em;
    private final ItemService service;

    Long userId;
    User user;
    Item item;

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

        String itemName = "itemName";
        String description = "description";
        Boolean isAvailable = true;
        item = Item.builder()
                .name(itemName)
                .description(description)
                .available(isAvailable)
                .ownerId(userId)
                .build();
        em.persist(item);
    }

    @Test
    void findItemsByUserTest() {
        List<ItemDtoWithComments> itemList = service.findItemsByUser(userId);

        assertThat(item.getName(), equalTo((itemList.getFirst().getName())));
        assertThat(item.getDescription(), equalTo((itemList.getFirst().getDescription())));
        assertThat(item.getAvailable(), equalTo(itemList.getFirst().getAvailable()));
    }
}
