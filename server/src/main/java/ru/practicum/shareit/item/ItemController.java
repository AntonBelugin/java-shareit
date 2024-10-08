package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.Constants;
import ru.practicum.shareit.item.dto.*;

import java.util.List;

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDtoResponse create(@RequestHeader(Constants.HEADER_USER) long userId, @RequestBody ItemDtoRequest item) {
        return itemService.add(userId, item);
    }

    @GetMapping("/{id}")
    public ItemDtoWithComments findById(@PathVariable long id) {
        return itemService.findById(id);
    }

    @PatchMapping("/{id}")
    public ItemDtoResponse update(@RequestHeader(Constants.HEADER_USER) long userId, @PathVariable long id,
                                  @RequestBody ItemDtoRequest item) {
        return itemService.update(userId, id, item);
    }

    @GetMapping
    public List<ItemDtoWithComments> findItemsByUser(@RequestHeader(Constants.HEADER_USER) long userId) {
        return itemService.findItemsByUser(userId);
    }

    @GetMapping("/search")
    public List<ItemDtoResponse> searchByText(@RequestParam String text) {
        return itemService.searchByText(text);
    }

    @PostMapping("/{id}/comment")
    public CommentDtoResponse create(@RequestHeader(Constants.HEADER_USER) long userId,
                                     @PathVariable long id, @Valid @RequestBody CommentDtoRequest comment) {
        return itemService.addComment(userId, id, comment);
    }
}