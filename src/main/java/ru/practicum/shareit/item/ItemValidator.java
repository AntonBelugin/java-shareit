package ru.practicum.shareit.item;

import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;

public class ItemValidator {

    static void validateNotNull(Item item) {
        if ((item.getName() == null) || item.getName().isBlank()) {
            throw new ValidationException("Не указано наименование");
        }
        if ((item.getDescription() == null) || item.getDescription().isBlank()) {
            throw new ValidationException("Не указано описание");
        }
        if (item.getAvailable() == null) {
            throw new ValidationException("Не указан статус");
        }
    }
}
