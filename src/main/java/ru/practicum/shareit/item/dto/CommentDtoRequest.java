package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CommentDtoRequest {
    @NotBlank
    @Length(max = 512)
    String text;
}
