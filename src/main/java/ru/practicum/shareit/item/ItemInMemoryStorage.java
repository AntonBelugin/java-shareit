package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserStorage;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class ItemInMemoryStorage implements ItemStorage {
    Map<Long, Item> items = new HashMap<>();
    Long currentId = 0L;
    final UserStorage userStorage;

    @Override
    public Item add(long userId, Item item) {
        item.setId(getNextId());
        items.put(item.getId(), item);
        userStorage.findById(userId).getItemsUser().put(item.getId(), item);
        return item;
    }

    @Override
    public Item findById(long id) {
        return items.get(id);
    }

    @Override
    public Item update(long userId, Item item) {
        items.put(item.getId(), item);
        userStorage.findById(userId).getItemsUser().put(item.getId(), item);
        return item;
    }

    @Override
    public void testItem(long itemId) {
        if (!items.containsKey(itemId)) {
            throw new NotFoundException("Вещи с id " + itemId + " не существует");
        }
    }

    @Override
    public List<Item> searchByText(String text) {
        List<Item> itemsSearch = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return itemsSearch;
        }
        for (Item item : items.values()) {
            if (((item.getName().toLowerCase().contains(text.toLowerCase()))
                    || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                    && item.getAvailable()) {
                itemsSearch.add(item);
            }
        }
        return itemsSearch;
    }

    @Override
    public Collection<Item> findAll() {
        return items.values();
    }

    private long getNextId() {
        return ++currentId;
    }
}
