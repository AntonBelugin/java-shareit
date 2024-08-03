package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

public interface UserStorage {
    User add(User user);

    User findById(long id);

    void deleteById(long id);

    User update(long id, User user);

    void testUser(long id);
}
