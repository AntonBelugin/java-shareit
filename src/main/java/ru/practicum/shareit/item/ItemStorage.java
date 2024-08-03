package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;

interface ItemStorage {
    Item add(long id, Item item);

    Item findById(long id);

    Item update(long id, Item item);

    void testItem(long id);

    List<Item> searchByText(String text);

    Collection<Item> findAll();
}
