package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    final ItemRepository itemRepository;
    final UserService userService;
    final BookingRepository bookingRepository;
    final CommentRepository commentRepository;

    @Override
    public ItemDto add(long userId, ItemDto item) {
        userService.findById(userId);
        Item newItem = ItemMapper.modelFromDto(item);
        newItem.setOwnerId(userId);
        Item addItem = itemRepository.save(newItem);
        return ItemMapper.modelToDto(addItem);
    }

    @Override
    public ItemDto findById(long userId, long id) {
        Item item = getItem(id);
        ItemDto itemDto = ItemMapper.modelToDto(item);
        if (userId == item.getOwnerId()) {
            List<Booking> bookings = bookingRepository.
                    findAllByItemIdAndEndBeforeOrderByStartDesc(id, LocalDateTime.now());
            if (!bookings.isEmpty()) {
                Booking lastBooking = bookings.getFirst();
                itemDto.setLastBooking(lastBooking.getStart());
            }
            bookings = bookingRepository.
                    findAllByItemIdAndStartAfterOrderByStartDesc(id, LocalDateTime.now());
            if (!bookings.isEmpty()) {
                Booking nextBooking = bookings.getFirst();
                itemDto.setNextBooking(nextBooking.getStart());
            }
        }
        return itemDto;
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto item) {
        Item oldItem = getItem(itemId);
        checkOwner(userId, itemId);
        oldItem.setName(Optional.ofNullable(item.getName()).filter(name
                -> !name.isBlank()).orElse(oldItem.getName()));
        oldItem.setDescription(Optional.ofNullable(item.getDescription()).filter(description
                -> !description.isBlank()).orElse(oldItem.getDescription()));
        oldItem.setAvailable(Optional.ofNullable(item.getAvailable()).filter(available
                -> available.describeConstable().isPresent()).orElse(oldItem.getAvailable()));
        item.setId(itemId);
        return ItemMapper.modelToDto(itemRepository.save(oldItem));
    }

    @Override
    public List<ItemDto> findItemsByUser(long id) {
        return  itemRepository.findByOwnerId(id).stream()
             .map(ItemMapper::modelToDto).toList();
    }

    @Override
    public List<ItemDto> searchByText(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        } else {
            return itemRepository.
                    findByAvailableTrueAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text, text)
                    .stream()
                    .map(ItemMapper::modelToDto).toList();
        }
    }

    @Override
    public CommentDto addComment(long userId, long itemId, CommentDto comment) {
        User user = UserMapper.modelFromDto(userService.findById(userId));
        Item item = ItemMapper.modelFromDto(findById(userId, itemId));
        System.out.println(bookingRepository.
                findAllByBookerIdAndItemIdAndEndBefore(userId, itemId, LocalDateTime.now()));
        if (bookingRepository.
                findAllByBookerIdAndItemIdAndEndBefore(userId, itemId, LocalDateTime.now()).isEmpty()) {
            throw new ValidationException("Вы не можете оставить комментарий," +
                    " потому что не бронировали вещь с id " + itemId);
        }
        Comment addComment = CommentMapper.modelFromDto(comment);
        addComment.setAuthor(user);
        addComment.setItem(item);
        addComment.setCreated(LocalDateTime.now());
        return CommentMapper.modelToDto(commentRepository.save(addComment));
    }

    @Override
    public List<ItemDto> findAll() {
        return itemRepository.findAll().stream()
                .map(ItemMapper::modelToDto)
                .toList();
    }

    private void checkOwner(long userId, long itemId) {
        if (userId != getItem(itemId).getOwnerId()) {
            throw new NotFoundException("Вещь с id " + itemId +
                    " не принадлежит пользователю с id " + userId);
        }
    }

    private Item getItem(long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new NotFoundException("Вещь с id " + id + " не существует");
        }
    }
}
