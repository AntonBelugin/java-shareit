package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

interface BookingService {
   // ItemDto findById(long id);

    BookingDto add(long id, BookingDto booking);

   /*ItemDto update(long userId, long itemId, BookingDto booking);

    List<ItemDto> findItemsByUser(long id);

    List<ItemDto> searchByText(String text);

    List<ItemDto> findAll();*/
}
