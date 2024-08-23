package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId, @Valid @RequestBody ItemDto item) {
        return itemService.add(userId, item);
    }

    @GetMapping("/{id}")
    public ItemDto findById(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long id) {
        return itemService.findById(userId, id);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long id,
                          @RequestBody ItemDto item) {
        return itemService.update(userId, id, item);
    }

    @GetMapping
    public List<ItemDto> findItemsByUser(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.findItemsByUser(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchByText(@RequestParam String text) {
        return itemService.searchByText(text);
    }

    @GetMapping("/all")
    public List<ItemDto> findAll() {
        return itemService.findAll();
    }

    @PostMapping("/{id}/comment")
    public CommentDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                             @PathVariable long id, @Valid @RequestBody CommentDto comment) {
        return itemService.addComment(userId, id, comment);
    }
}