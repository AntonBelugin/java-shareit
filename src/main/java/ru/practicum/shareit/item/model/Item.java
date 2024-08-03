package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * TODO Sprint add-controllers.
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    Long id;
    @NotNull
    String name;
    @NotNull
    String description;
    @NotNull
    Boolean available;
    long ownerId;
}
