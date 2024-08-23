package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto findById(long userId, long id);

    ItemDto add(long id, ItemDto item);

    ItemDto update(long userId, long itemId, ItemDto item);

    List<ItemDto> findItemsByUser(long id);

    List<ItemDto> searchByText(String text);

    CommentDto addComment(long userId, long itemId, CommentDto comment);

    List<ItemDto> findAll();
}
