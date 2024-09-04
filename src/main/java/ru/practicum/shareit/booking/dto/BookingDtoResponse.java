package ru.practicum.shareit.booking.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDtoForBooking;
import ru.practicum.shareit.user.dto.UserDtoForBooking;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class BookingDtoResponse {
    long id;
    ItemDtoForBooking item;
    UserDtoForBooking booker;
    LocalDateTime start;
    LocalDateTime end;
    BookingStatus status;
}
