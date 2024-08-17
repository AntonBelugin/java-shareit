package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserStorage;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    final ItemStorage itemStorage;
    final UserStorage userStorage;

    @Override
    public ItemDto add(long userId, ItemDto item) {
        userStorage.checkUser(userId);
        Item newItem = ItemMapper.modelFromDto(item);
        newItem.setOwnerId(userId);
        Item addItem = itemStorage.add(userId, newItem);
        userStorage.findById(userId).getItemsUser().put(addItem.getId(), addItem);
        return ItemMapper.modelToDto(addItem);
    }

    @Override
    public ItemDto findById(long id) {
        itemStorage.checkItem(id);
        return ItemMapper.modelToDto(itemStorage.findById(id));
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto item) {
        itemStorage.checkItem(itemId);
        userStorage.checkUser(userId);
        checkOwner(userId, itemId);
        Item oldItem = itemStorage.findById(itemId);
        oldItem.setName(Optional.ofNullable(item.getName()).filter(name
                -> !name.isBlank()).orElse(oldItem.getName()));
        oldItem.setDescription(Optional.ofNullable(item.getDescription()).filter(description
                -> !description.isBlank()).orElse(oldItem.getDescription()));
        oldItem.setAvailable(Optional.ofNullable(item.getAvailable()).filter(available
                -> available.describeConstable().isPresent()).orElse(oldItem.getAvailable()));
        item.setId(itemId);
        return ItemMapper.modelToDto(itemStorage.update(userId, oldItem));
    }

    @Override
    public List<ItemDto> findItemsByUser(long id) {
        userStorage.checkUser(id);
        return userStorage.findById(id).getItemsUser().values().stream()
                .map(ItemMapper::modelToDto).toList();
    }

    @Override
    public List<ItemDto> searchByText(String text) {
        return itemStorage.searchByText(text).stream()
                .map(ItemMapper::modelToDto).toList();
    }

    private void checkOwner(long userId, long itemId) {
        if (userId != itemStorage.findById(itemId).getOwnerId()) {
            throw new NotFoundException("Вещь с id " + itemId +
                    " не принадлежит пользователю с id " + userId);
        }
    }

    @Override
    public List<ItemDto> findAll() {
        return itemStorage.findAll().stream()
                .map(ItemMapper::modelToDto)
                .toList();
    }
}
