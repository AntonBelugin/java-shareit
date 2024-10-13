package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.Constants;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.validation.ValidationMarker;

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    @Validated({ValidationMarker.OnCreate.class})
    public ResponseEntity<Object> createComment(@RequestHeader(Constants.HEADER_USER) long userId,
                                                @Valid @RequestBody ItemDtoRequest item) {
        return itemClient.createItem(userId, item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id) {
        return itemClient.getItemById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestHeader(Constants.HEADER_USER) long userId, @PathVariable long id,
                                  @RequestBody @Valid ItemDtoRequest item) {
        return itemClient.updateItem(userId, id, item);
    }

    @GetMapping
    public ResponseEntity<Object> findItemsByUser(@RequestHeader(Constants.HEADER_USER) long userId,
                                                  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return itemClient.getAllItemsByOwnerId(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchByText(@RequestParam String text,
                                               @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                               @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        if (text.isBlank()) {
            return ResponseEntity.noContent().build();
        }
        return itemClient.searchItems(text, from, size);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader(Constants.HEADER_USER) long userId,
                                                @PathVariable long id, @Valid @RequestBody CommentDtoRequest comment) {
        return itemClient.addComment(userId, id, comment);
    }
}