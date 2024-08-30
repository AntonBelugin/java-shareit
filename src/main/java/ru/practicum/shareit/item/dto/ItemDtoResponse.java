package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class ItemDtoResponse {
    long id;
    String name;
    String description;
    Boolean available;
    LocalDateTime lastBooking;
    LocalDateTime nextBooking;
}