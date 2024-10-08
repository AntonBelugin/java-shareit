package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto findById(long id);

    UserDto add(UserDto user);

    void deleteById(long id);

    UserDto update(long id, UserDto user);
}
