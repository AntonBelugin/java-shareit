package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.Constants;
import ru.practicum.shareit.request.dto.RequestDto;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {
    final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> addItemRequest(@RequestHeader(Constants.HEADER_USER) long userId,
                                                 @RequestBody @Valid RequestDto requestDto) {
        return requestClient.addItemRequest(userId, requestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getRequests(@RequestHeader(Constants.HEADER_USER) long userId,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return requestClient.getRequests(userId, from, size);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(Constants.HEADER_USER) long userId,
                                                 @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return requestClient.getAllRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@PathVariable Long requestId) {
        return requestClient.getRequestById(requestId);
    }
}
