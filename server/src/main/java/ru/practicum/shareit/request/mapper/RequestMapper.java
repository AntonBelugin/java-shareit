package ru.practicum.shareit.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDtoForRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.dto.RequestWithItemDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.model.User;

import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class RequestMapper {
    public static Request toRequest(RequestDto requestDto, User searcher) {
        return Request.builder()
                .description(requestDto.getDescription())
                .searcher(searcher)
                .build();
    }

    public static RequestResponseDto toRequestResponseDto(Request request) {
        return RequestResponseDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .build();
    }

    public static RequestWithItemDto toRequestWithItemDto(Request request, List<Item> items) {
        List<ItemDtoForRequest> itemsDtoForRequests = items.stream()
                .map(ItemMapper::modelToDtoForRequest)
                .toList();
        return RequestWithItemDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .items(itemsDtoForRequests)
                .build();
    }

    public static List<RequestWithItemDto> toRequestsWithItemDto(List<Request> requests, List<Item> items) {
        List<RequestWithItemDto> requestsWithItemDto = new ArrayList<>();

        Map<Long, List<Item>> itemsMap = Objects.isNull(items) ? new HashMap<>() : items.stream()
                .collect(Collectors.groupingBy(i -> i.getRequest().getId()));
        for (Request itemRequest : requests) {
            requestsWithItemDto.add(RequestWithItemDto.builder()
                    .id(itemRequest.getId())
                    .description(itemRequest.getDescription())
                    .created(itemRequest.getCreated())
                    .items(ItemMapper.toItemForItemRequestDto(itemsMap.get(itemRequest.getId())))
                    .build());
        }
        return requestsWithItemDto;
    }
}
