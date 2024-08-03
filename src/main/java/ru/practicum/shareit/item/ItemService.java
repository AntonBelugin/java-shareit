package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

interface ItemService {
    ItemDto findById(long id);

    ItemDto add(long id, Item item);

    ItemDto update(long userId, long itemId, Item item);

    List<ItemDto> findItemsByUser(long id);

    List<ItemDto> searchByText(String text);

    List<ItemDto> findAll();
}
