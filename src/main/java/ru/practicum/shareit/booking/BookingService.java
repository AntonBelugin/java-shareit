package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto add(long id, BookingDto booking);

    BookingDto approved(long userId, long bookingId, Boolean approved);

    BookingDto findById(long userId, long id);

    List<BookingDto> getAllByBooker(long userId, String state);

    List<BookingDto> getAllByOwner(long ownerId, String state);
}
