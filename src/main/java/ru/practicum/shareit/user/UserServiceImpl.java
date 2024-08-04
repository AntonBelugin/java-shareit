package ru.practicum.shareit.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserStorage userStorage;

    @Override
    public UserDto add(UserDto user) {
       // UserValidator.validateNotNull(user);
       // UserValidator.validateFormat(user);
        return UserMapper.modelToDto(userStorage.add(UserMapper.modelFromDto(user)));
    }

    @Override
    public UserDto findById(long id) {
        userStorage.checkUser(id);
        return UserMapper.modelToDto(userStorage.findById(id));
    }

    @Override
    public void deleteById(long id) {
        userStorage.checkUser(id);
        userStorage.deleteById(id);
    }

    @Override
    public UserDto update(long id, UserDto user) {
        userStorage.checkUser(id);
        UserValidator.validateFormat(user);
        User oldUser = userStorage.findById(id);
        oldUser.setEmail(Optional.ofNullable(user.getEmail()).filter(email
                -> !email.isBlank()).orElse(oldUser.getEmail()));
        oldUser.setName(Optional.ofNullable(user.getName()).filter(name
                -> !name.isBlank()).orElse(oldUser.getName()));
        return UserMapper.modelToDto(userStorage.update(id, oldUser));
    }
}
