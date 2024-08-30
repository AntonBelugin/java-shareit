package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    final ItemRepository itemRepository;
    final UserRepository userRepository;
    final BookingRepository bookingRepository;
    final CommentRepository commentRepository;

    @Override
    public ItemDtoResponse add(long userId, ItemDtoRequest item) {
        userRepository.getUserById(userId);
        Item newItem = ItemMapper.modelFromDto(item);
        newItem.setOwnerId(userId);
        Item addItem = itemRepository.save(newItem);
        return ItemMapper.modelToDtoResponse(addItem);
    }

    @Override
    public ItemDtoWithComments findById(long userId, long id) {
        Item item = itemRepository.getItemById(id);
        ItemDtoWithComments itemDto = ItemMapper.mapToDtoWithComments(item);
        if (userId == item.getOwnerId()) {
            List<Booking> bookings = bookingRepository
                    .findAllByItemIdAndEndBeforeOrderByStartDesc(id, LocalDateTime.now());
            if (!bookings.isEmpty()) {
                Booking lastBooking = bookings.getFirst();
                itemDto.setLastBooking(lastBooking.getStart());
            }
            bookings = bookingRepository
                    .findAllByItemIdAndStartAfterOrderByStartDesc(id, LocalDateTime.now());
            if (!bookings.isEmpty()) {
                Booking nextBooking = bookings.getFirst();
                itemDto.setNextBooking(nextBooking.getStart());
            }
        }
        itemDto.setComments(commentRepository.findByItemId(id).stream()
                .map(CommentMapper::modelToDtoResponse)
                .toList());
        return itemDto;
    }

    @Override
    public ItemDtoResponse update(long userId, long itemId, ItemDtoRequest item) {
        Item oldItem = itemRepository.getItemById(itemId);
        checkOwner(userId, itemId);
        oldItem.setName(Optional.ofNullable(item.getName()).filter(name
                -> !name.isBlank()).orElse(oldItem.getName()));
        oldItem.setDescription(Optional.ofNullable(item.getDescription()).filter(description
                -> !description.isBlank()).orElse(oldItem.getDescription()));
        oldItem.setAvailable(Optional.ofNullable(item.getAvailable()).filter(available
                -> available.describeConstable().isPresent()).orElse(oldItem.getAvailable()));
        return ItemMapper.modelToDtoResponse(itemRepository.save(oldItem));
    }

    @Override
    public List<ItemDtoWithComments> findItemsByUser(long id) {
        Map<Long, Item> itemMap = itemRepository.findAllByOwnerId(id)
                .stream()
                .collect(Collectors.toMap(Item::getId, Function.identity()));
        Map<Long, List<Comment>> commentMap = commentRepository.findAllByItemIdIn((itemMap.keySet()))
                .stream()
                .collect(Collectors.groupingBy(Comment::getItemId));
        return itemMap.values()
                .stream()
                .map(item -> makePostWithCommentsDto(
                        item,
                        commentMap.getOrDefault(item.getId(), Collections.emptyList())
                ))
                .collect(Collectors.toList());
    }

    private ItemDtoWithComments makePostWithCommentsDto(Item item, List<Comment> comments) {
        List<CommentDtoResponse
                > commentDtos = comments
                .stream()
                .map(CommentMapper::modelToDtoResponse)
                .collect(Collectors.toList());
        ItemDtoWithComments itemDtoWithComments = ItemMapper.mapToDtoWithComments(item);
        itemDtoWithComments.setComments(commentDtos);
        return itemDtoWithComments;
    }

    @Override
    public List<ItemDtoResponse> searchByText(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        } else {
            return itemRepository
                    .findByAvailableTrueAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text, text)
                    .stream()
                    .map(ItemMapper::modelToDtoResponse).toList();
        }
    }

    @Override
    public CommentDtoResponse addComment(long userId, long itemId, CommentDtoRequest comment) {
        User user = userRepository.getUserById(userId);
        itemRepository.getItemById(itemId);
        if (bookingRepository
                .findAllByBookerIdAndItemIdAndEndBefore(userId, itemId, LocalDateTime.now()).isEmpty()) {
            throw new ValidationException("Вы не можете оставить комментарий," +
                    " потому что не бронировали вещь с id " + itemId);
        }
        Comment addComment = CommentMapper.modelFromDto(comment);
        addComment.setAuthor(user);
        addComment.setItemId(itemId);
        addComment.setCreated(LocalDateTime.now());
        return CommentMapper.modelToDtoResponse(commentRepository.save(addComment));
    }

    @Override
    public List<ItemDtoResponse> findAll() {
        return itemRepository.findAll().stream()
                .map(ItemMapper::modelToDtoResponse)
                .toList();
    }

    private void checkOwner(long userId, long itemId) {
        if (userId != itemRepository.getItemById(itemId).getOwnerId()) {
            throw new NotFoundException("Вещь с id " + itemId +
                    " не принадлежит пользователю с id " + userId);
        }
    }
}
