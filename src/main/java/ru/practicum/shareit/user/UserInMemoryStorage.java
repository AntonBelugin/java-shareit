package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ConflictDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class UserInMemoryStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long currentId = 0L;

    @Override
    public User add(User user) {
        if (users.containsValue(user)) {
            throw new ConflictDataException("Такой email уже используется");
        }
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User findById(long id) {
        return users.get(id);
    }

    @Override
    public void deleteById(long id) {
        users.remove(id);
    }

    @Override
    public User update(long id, User user) {
        checkEmail(user);
        users.put(id, user);
        return user;
    }

    @Override
    public void checkUser(long userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException("Пользователя с id " + userId + " не существует");
        }
    }

    private long getNextId() {
        return ++currentId;
    }

    private void checkEmail(User user) {
        for (User userOld : users.values()) {
            if (Objects.equals(userOld.getEmail(), user.getEmail())
                    && !Objects.equals(user.getId(), userOld.getId())) {
                   throw new ConflictDataException("Такой email уже используется");
            }
        }
    }
}
