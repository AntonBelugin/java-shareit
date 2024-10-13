package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.dto.RequestWithItemDto;

import java.util.Collection;

public interface RequestService {

    RequestResponseDto create(Long userId, RequestDto requestDto);

    RequestWithItemDto getRequestById(Long requestId);

    Collection<RequestWithItemDto> getRequestsBySearcherId(Long searcherId, Integer from, Integer size);
}