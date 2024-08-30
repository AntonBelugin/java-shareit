package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.util.List;

public interface BookingService {
    BookingDtoResponse add(long id, BookingDtoRequest booking);

    BookingDtoResponse approved(long userId, long bookingId, Boolean approved);

    BookingDtoResponse findById(long userId, long id);

    List<BookingDtoResponse> getAllByBooker(long userId, String state);

    List<BookingDtoResponse> getAllByOwner(long ownerId, String state);
}
