package ru.practicum.shareit.booking;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.mapper.BookingMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    final ItemStorage itemStorage;
    final ItemRepository itemRepository;
    final UserRepository userRepository;
    final UserService userService;
    final BookingRepository bookingRepository;

    @Override
    public BookingDto add(long userId, BookingDto booking) {
        userService.findById(userId);
        Booking newBooking = BookingMapper.modelFromDto(booking);
        newBooking.setBooker(userId);
        Booking addBooking = bookingRepository.save(newBooking);
        // userStorage.findById(userId).getItemsUser().put(addItem.getId(), addItem);
        return BookingMapper.modelToDto(addBooking);
    }

   /* @Override
    public ItemDto findById(long id) {
        return ItemMapper.modelToDto(getItem(id));
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto item) {
        Item oldItem = getItem(itemId);
        //  userStorage.checkUser(userId);
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

        //   userStorage.checkUser(id);
        return  itemRepository.findByOwnerId(id).stream()
                .map(ItemMapper::modelToDto).toList();
    }

    @Override
    public List<ItemDto> searchByText(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<ItemDto>();
        } else {
            return itemRepository.
                    findByAvailableTrueAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text, text)
                    .stream()
                    .map(ItemMapper::modelToDto).toList();
        }
    }*/

    private void checkOwner(long userId, long itemId) {
        if (userId != getItem(itemId).getOwnerId()) {
            throw new NotFoundException("Вещь с id " + itemId +
                    " не принадлежит пользователю с id " + userId);
        }
    }

 /*   @Override
    public List<ItemDto> findAll() {
        return itemRepository.findAll().stream()
                .map(ItemMapper::modelToDto)
                .toList();
    }*/

    private Item getItem(long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new NotFoundException("Вещь с id " + id + " не существует");
        }
    }
}

