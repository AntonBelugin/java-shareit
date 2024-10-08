package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.constants.Constants;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.user.dto.UserDtoForBooking;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookingController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingControllerTest {
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;
    private static BookingDtoRequest bookingDtoRequest;
    private static BookingDtoResponse bookingDtoResponse;
    private static ItemDtoForBooking itemDtoForBooking;
    private static UserDtoForBooking userDtoForBooking;
    private static Long itemId;
    private static Long userId;
    private static Long bookingId;

    @MockBean
    private BookingService bookingService;

    @BeforeAll
    static void setUp() {
        itemId = 1L;
        userId = 1L;
        bookingId = 1L;
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusDays(2);
        itemDtoForBooking = ItemDtoForBooking.builder()
                .id(itemId)
                .name("name")
                .build();
        userDtoForBooking = UserDtoForBooking.builder()
                .id(userId)
                .build();
        bookingDtoRequest = BookingDtoRequest.builder()
                .itemId(itemId)
                .start(start)
                .end(end)
                .build();
        bookingDtoResponse = BookingDtoResponse.builder()
                .id(bookingId)
                .item(itemDtoForBooking)
                .booker(userDtoForBooking)
                .start(start)
                .end(end)
                .status(BookingStatus.APPROVED)
                .build();
    }

    @Test
    @SneakyThrows
    void createTest() {
        Mockito.when(bookingService.add(userId, bookingDtoRequest)).thenReturn(bookingDtoResponse);

        mockMvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingDtoRequest))
                        .header(Constants.HEADER_USER, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDtoResponse.getId()), Long.class))
                .andExpect(jsonPath("$.start", is(bookingDtoResponse.getStart()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
                .andExpect(jsonPath("$.end", is(bookingDtoResponse.getEnd()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))));
    }

    @Test
    @SneakyThrows
    void approvedTest() {
        Mockito.when(bookingService.approved(userId, bookingId, true)).thenReturn(bookingDtoResponse);

        mockMvc.perform(patch("/bookings/" + bookingId + "?approved={approved}", true)
                        .header(Constants.HEADER_USER, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDtoResponse.getId()), Long.class))
                .andExpect(jsonPath("$.start", is(bookingDtoResponse.getStart()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
                .andExpect(jsonPath("$.end", is(bookingDtoResponse.getEnd()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
                .andExpect(jsonPath("$.status", is(bookingDtoResponse.getStatus().name())));
    }

    @Test
    @SneakyThrows
    void findByIdTest() {
        Mockito.when(bookingService.findById(userId, bookingId)).thenReturn(bookingDtoResponse);

        mockMvc.perform(get("/bookings/{id}", bookingId)
                        .header(Constants.HEADER_USER, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDtoResponse.getId()), Long.class))
                .andExpect(jsonPath("$.start", is(bookingDtoResponse.getStart()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
                .andExpect(jsonPath("$.end", is(bookingDtoResponse.getEnd()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
                .andExpect(jsonPath("$.status", is(bookingDtoResponse.getStatus().name())));
    }

    @Test
    @SneakyThrows
    void getAllByBookerTest() {
        List<BookingDtoResponse> listBooking = List.of(bookingDtoResponse);
        Mockito.when(bookingService.getAllByBooker(userId, "all")).thenReturn(listBooking);

        mockMvc.perform(get("/bookings?state={state}", "all")
                        .header(Constants.HEADER_USER, userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Collections.singletonList(bookingDtoResponse))));
    }

    @Test
    @SneakyThrows
    void getAllByOwnerTest() {
        List<BookingDtoResponse> listBooking = List.of(bookingDtoResponse);
        Mockito.when(bookingService.getAllByOwner(userId, "all")).thenReturn(listBooking);

        mockMvc.perform(get("/bookings/owner?state={state}", "all")
                        .header(Constants.HEADER_USER, userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Collections.singletonList(bookingDtoResponse))));
    }
}
