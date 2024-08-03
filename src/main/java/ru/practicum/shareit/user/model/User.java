package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO Sprint add-controllers.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(of = {"email"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    Long id;
    @NotNull
    String name;
    @NotNull
    String email;
    @Builder.Default
    Map<Long, Item> itemsUser = new HashMap<>();
}
