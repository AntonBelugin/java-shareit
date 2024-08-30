package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.*;

import java.util.List;

public interface ItemService {
    ItemDtoWithComments findById(long userId, long id);

    ItemDtoResponse add(long id, ItemDtoRequest item);

    ItemDtoResponse update(long userId, long itemId, ItemDtoRequest item);

    List<ItemDtoWithComments> findItemsByUser(long id);

    List<ItemDtoResponse> searchByText(String text);

    CommentDtoResponse addComment(long userId, long itemId, CommentDtoRequest comment);

    List<ItemDtoResponse> findAll();
}
