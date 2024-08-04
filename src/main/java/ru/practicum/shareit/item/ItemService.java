package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

interface ItemService {
    ItemDto findById(long id);

    ItemDto add(long id, ItemDto item);

    ItemDto update(long userId, long itemId, ItemDto item);

    List<ItemDto> findItemsByUser(long id);

    List<ItemDto> searchByText(String text);

    List<ItemDto> findAll();
}
