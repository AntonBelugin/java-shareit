package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ItemDtoWithComments {
    long id;
    String name;
    String description;
    Boolean available;
    List<CommentDtoResponse> comments;
    LocalDateTime lastBooking;
    LocalDateTime nextBooking;
}