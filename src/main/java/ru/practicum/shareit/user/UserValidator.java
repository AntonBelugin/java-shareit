package ru.practicum.shareit.user;

import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

public class UserValidator {

    static void validateNotNull(User user) {
        if (user.getEmail() == null) {
            throw new ValidationException("Не указана электронная почта");
        }
        if (user.getName() == null) {
            throw new ValidationException("Не указано имя");
        }
    }

    static void validateFormat(User user) {
        if (!(user.getEmail() == null)) {
            if (!user.getEmail().contains("@")) {
                throw new ValidationException("Неправильный Имейл");
            }
        }
    }
}
