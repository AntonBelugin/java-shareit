package ru.practicum.shareit.booking;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.exception.ConflictDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    final ItemRepository itemRepository;
    final UserService userService;
    final ItemService itemService;
    final BookingRepository bookingRepository;

    @Override
    public BookingDto add(long userId, BookingDto booking) {
        User booker = UserMapper.modelFromDto(userService.findById(userId));
        checkItem(booking.getItemId());
        Item item = ItemMapper.modelFromDto(itemService.findById(userId, booking.getItemId()));
        Booking newBooking = BookingMapper.modelFromDto(booking, booker, item);
        newBooking.setStatus(BookingStatus.WAITING);
        Booking addBooking = bookingRepository.save(newBooking);
        return BookingMapper.modelToDto(addBooking);
    }

    @Override
    public BookingDto approved(long userId, long bookingId, Boolean approved) {
        Booking booking = getBooking(bookingId);
        checkRightApproved(booking, userId);
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return BookingMapper.modelToDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto findById(long userId, long id) {
        Booking booking = getBooking(id);
        checkRightLook(booking, userId);
        return BookingMapper.modelToDto(booking);
    }

    @Override
    public List<BookingDto> getAllByBooker(long bookerId, String stateStr) {
        BookingState state = BookingState.findBy(stateStr);
        userService.findById(bookerId);
        switch (state) {
            case ALL:
                return bookingRepository.findByBookerIdOrderByStartDesc(bookerId).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            case WAITING:
                return bookingRepository.
                        findAllByBookerIdAndStatusOrderByStartDesc(bookerId, BookingStatus.WAITING).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            case PAST:
                return bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(bookerId,
                                LocalDateTime.now()).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            case FUTURE:
                return bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(bookerId,
                                LocalDateTime.now()).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            case REJECTED:
                return bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(bookerId,
                                BookingStatus.REJECTED).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            case CURRENT:
                return bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(bookerId,
                                LocalDateTime.now(), LocalDateTime.now()).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            default:
                throw new ConflictDataException("Введен неправильный статус");
        }
    }

    @Override
    public List<BookingDto> getAllByOwner(long ownerId, String stateStr) {
        BookingState state = BookingState.valueOf(stateStr.toUpperCase()); //исключение ?
        userService.findById(ownerId);// если есть вещь
        System.out.println(ownerId);
        switch (state) {
            case ALL:
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(ownerId).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            case WAITING:
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(ownerId, BookingStatus.WAITING).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            case PAST:
                return bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(ownerId,
                                LocalDateTime.now()).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            case FUTURE:
                return bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(ownerId,
                                LocalDateTime.now()).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            case REJECTED:
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(ownerId,
                                BookingStatus.REJECTED).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            case CURRENT:
                return bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(ownerId,
                                LocalDateTime.now(), LocalDateTime.now()).stream()
                        .map(BookingMapper::modelToDto)
                        .toList();
            default:
                throw new ConflictDataException("Введен неправильный статус");
        }

    }

    private void checkItem(long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            if (item.get().getAvailable().equals(false)) {
                throw new ValidationException("Вещь недоступна для бронирования");
            }
        } else {
            throw new NotFoundException("Вещь с id " + id + " не существует");
        }
    }

    private void checkRightLook(Booking booking, long userId) {
        if (!booking.getBooker().getId().equals(userId) && !itemRepository.findById(booking.getItem().getId()).
                get().getOwnerId().equals(userId)) {
            throw new ValidationException("Вам недоступен просмотр бронирования");
            }
    }

    private void checkRightApproved(Booking booking, long userId) {
        if (!itemRepository.findById(booking.getItem().getId()).
                get().getOwnerId().equals(userId)) {
            throw new AccessDeniedException("Вам недоступно подтверждение бронирования");
        }
    }

    private Booking getBooking(long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            return booking.get();
        } else {
            throw new NotFoundException("Бронирование с id " + id + " не существует");
        }
    }
}