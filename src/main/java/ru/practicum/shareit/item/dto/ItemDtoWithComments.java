package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class ItemDtoWithComments {
    long id;
    String name;
    String description;
    Boolean available;
    List<CommentDtoResponse> comments;
    LocalDateTime lastBooking;
    LocalDateTime nextBooking;
}