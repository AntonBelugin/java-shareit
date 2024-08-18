package ru.practicum.shareit.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
//import ru.practicum.shareit.user.model.UserRepository;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserStorage userStorage;
    final UserRepository repository;

    @Override
    public UserDto add(UserDto user) {
        checkEmail(user);
        //return null;
       return UserMapper.modelToDto(repository.save(UserMapper.modelFromDto(user)));  //add(UserMapper.modelFromDto(user)));
    }

    @Override
    public UserDto findById(long id) {
        return UserMapper.modelToDto(getUser(id));

    }

    @Override
    public void deleteById(long id) {
        getUser(id);
        repository.deleteById(id);
    }

    @Override
    public UserDto update(long id, UserDto user) {
       // userStorage.checkUser(id);
        UserValidator.validateFormat(user);
        checkEmail(user);
        User oldUser = getUser(id);
        oldUser.setEmail(Optional.ofNullable(user.getEmail()).filter(email
                -> !email.isBlank()).orElse(oldUser.getEmail()));
        oldUser.setName(Optional.ofNullable(user.getName()).filter(name
                -> !name.isBlank()).orElse(oldUser.getName()));
        return UserMapper.modelToDto(repository.save(oldUser));
    }

    private User getUser(long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("Пользователя с id " + id + " не существует");
        }
    }

    private void checkEmail(UserDto user) {
        List<User> users = repository.findByEmailContainingIgnoreCase(user.getEmail());
        if (!users.isEmpty()) {
            if (user.getEmail().equals(users.getFirst().getEmail()) && user.getId() != users.getFirst().getId()) {
                throw new ConflictDataException("Такой email уже используется");
            }
        }
    }

}
