package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class BookingDtoRequest {
    @NotNull
    long itemId;
    @NotNull
    @FutureOrPresent
    LocalDateTime start;
    @NotNull
    @Future
    LocalDateTime end;
}
