package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Component
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId, @RequestBody Item item) {
        return itemService.add(userId, item);
    }

    @GetMapping("/{id}")
    public ItemDto findById(@PathVariable long id) {
        return itemService.findById(id);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long id, @RequestBody Item item) {
        return itemService.update(userId, id, item);
    }

    @GetMapping
    public List<ItemDto> findItemsByUser(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.findItemsByUser(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchByText(@RequestParam/*(defaultValue = "")*/ String text) {
        return itemService.searchByText(text);
    }

    @GetMapping("/all")
    public List<ItemDto> findAll() {
        return itemService.findAll();
    }
}