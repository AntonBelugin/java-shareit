package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.practicum.shareit.validation.ValidationMarker;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class ItemDtoRequest {
    @NotBlank(groups = ValidationMarker.OnCreate.class)
    @Length(max = 255)
    String name;
    @NotBlank(groups = ValidationMarker.OnCreate.class)
    @Length(max = 512)
    String description;
    @NotNull(groups = ValidationMarker.OnCreate.class)
    Boolean available;
}