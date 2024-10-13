package ru.practicum.shareit.request;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.Constants;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.dto.RequestWithItemDto;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/requests")
public class RequestController {
    final RequestService requestService;

    @PostMapping
    public RequestResponseDto addItemRequest(@RequestHeader(Constants.HEADER_USER) long userId,
                                                 @RequestBody RequestDto requestDto) {
        return requestService.create(userId, requestDto);
    }

    @GetMapping("/{requestId}")
    public RequestWithItemDto getRequest(@PathVariable Long requestId) {
        return requestService.getRequestById(requestId);
    }

    @GetMapping
    public Collection<RequestWithItemDto> getRequests(@RequestHeader(Constants.HEADER_USER) long searcherId,
                                                      @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return requestService.getRequestsBySearcherId(searcherId, from, size);
    }
}
