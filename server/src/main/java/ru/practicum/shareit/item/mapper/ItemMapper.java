package ru.practicum.shareit.item.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@UtilityClass
public final class ItemMapper {

    public Item modelFromDto(ItemDtoRequest item) {
        return Item.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public ItemDtoWithComments mapToDtoWithComments(Item item) {
        return ItemDtoWithComments.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public ItemDtoResponse modelToDtoResponse(Item item) {
        return ItemDtoResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public ItemDtoForRequest modelToDtoForRequest(Item item) {
        return ItemDtoForRequest.builder()
                .id(item.getId())
                .name(item.getName())
                .ownerId(item.getOwnerId())
                .build();
    }

    public static List<ItemDtoForRequest> toItemForItemRequestDto(Collection<Item> items) {
        return Objects.isNull(items) ? new ArrayList<>() : items.stream()
                .map(ItemMapper::modelToDtoForRequest)
                .toList();
    }
}
