package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class BookingDto {
    long id;
    @NotNull
    long itemId;
    Item item;
    User booker;
    @NotNull
    LocalDateTime start;
    @NotNull
    LocalDateTime end;
    BookingStatus status;
}
