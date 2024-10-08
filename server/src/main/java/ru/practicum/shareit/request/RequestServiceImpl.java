package ru.practicum.shareit.request;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.dto.RequestWithItemDto;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    final RequestRepository requestRepository;
    final UserRepository userRepository;
    final ItemRepository itemRepository;

    @Override
    public RequestResponseDto create(Long userId, RequestDto requestDto) {
        User searcher = userRepository.getUserById(userId);
        return RequestMapper.toRequestResponseDto(requestRepository.save(RequestMapper.toRequest(requestDto, searcher)));
    }

    @Override
    public RequestWithItemDto getRequestById(Long requestId) {
        return RequestMapper.toRequestWithItemDto(requestRepository.getItemRequestBy(requestId),
                itemRepository.findAllByRequestId(requestId));
    }

    @Override
    public Collection<RequestWithItemDto> getRequestsBySearcherId(Long searcherId, Integer from, Integer size) {
        userRepository.getUserById(searcherId);
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return RequestMapper.toRequestsWithItemDto(
                requestRepository.findBySearcherIdOrderByCreatedDesc(searcherId, page).getContent(),
                itemRepository.findAllByRequest_SearcherId(searcherId));
    }
}