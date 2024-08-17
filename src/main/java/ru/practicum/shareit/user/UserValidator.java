package ru.practicum.shareit.user;

import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

public class UserValidator {
    static void validateFormat(UserDto user) {
        if (!(user.getEmail() == null)) {
            if (!user.getEmail().contains("@")) {
                throw new ValidationException("Неправильный адрес электронной почты");
            }
        }
    }
}
