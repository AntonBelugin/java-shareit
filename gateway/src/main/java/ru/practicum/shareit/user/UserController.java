package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.validation.ValidationMarker;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> create(@Validated(ValidationMarker.OnCreate.class) @RequestBody UserRequestDto user) {
        return userClient.createUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id) {
        return userClient.getUserById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable long id,
                                         @Validated(ValidationMarker.OnUpdate.class)@RequestBody UserRequestDto user) {
        return userClient.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        userClient.deleteUser(id);
    }
}
