package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.Instant;

/**
 * TODO Sprint add-bookings.
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class BookingDto {
    Long id;
    @NotNull
    Long itemId;
    @NotNull
    //@Timestamp
    Instant start;
    @NotNull
    //@Timestamp
    Instant end;
    long status;
}
