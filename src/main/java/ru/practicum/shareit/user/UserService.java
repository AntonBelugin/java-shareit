package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

interface UserService {
    UserDto findById(long id);

    UserDto add(User user);

    void deleteById(long id);

    UserDto update(long id, User user);
}
