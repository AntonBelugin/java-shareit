package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.Constants;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.validation.ValidationMarker;

import java.util.List;

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @Validated({ValidationMarker.OnCreate.class})
    public ItemDtoResponse create(@RequestHeader(Constants.HEADER_USER) long userId, @Valid @RequestBody ItemDtoRequest item) {
        return itemService.add(userId, item);
    }

    @GetMapping("/{id}")
    public ItemDtoWithComments findById(@RequestHeader(Constants.HEADER_USER) long userId, @PathVariable long id) {
        return itemService.findById(userId, id);
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

    @GetMapping("/all")
    public List<ItemDtoResponse> findAll() {
        return itemService.findAll();
    }

    @PostMapping("/{id}/comment")
    public CommentDtoResponse create(@RequestHeader(Constants.HEADER_USER) long userId,
                                     @PathVariable long id, @Valid @RequestBody CommentDtoRequest comment) {
        return itemService.addComment(userId, id, comment);
    }
}