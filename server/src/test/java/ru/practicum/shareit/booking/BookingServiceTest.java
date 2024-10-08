package ru.practicum.shareit.booking;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@SpringBootTest(
        properties = "jdbc.url=jdbc:postgresql://localhost:5432/test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceTest {
    private final EntityManager em;
    private final BookingService service;

    Long userId;
    User user;
    Item item;
    Booking booking;

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

        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().minusDays(2);
        booking = Booking.builder()
                .booker(user)
                .item(item)
                .start(start)
                .end(end)
                .status(BookingStatus.WAITING)
                .build();
        em.persist(booking);
    }

    @Test
    void getAllByBookerTest() {
        List<BookingDtoResponse> bookingList = service.getAllByBooker(userId, "all");

        assertThat(booking.getBooker().getId(), equalTo((bookingList.getFirst().getBooker().getId())));
        assertThat(booking.getItem().getName(), equalTo((bookingList.getFirst().getItem().getName())));
        assertThat(booking.getStart(), equalTo((bookingList.getFirst().getStart())));
        assertThat(booking.getEnd(), equalTo(bookingList.getFirst().getEnd()));
        assertThat(booking.getStatus(), equalTo(bookingList.getFirst().getStatus()));
    }
}
