package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    default User getUserById(long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Пользователя с id " + id + " не существует"));
    }

    List<User> findByEmailContainingIgnoreCase(String emailSearch);
}