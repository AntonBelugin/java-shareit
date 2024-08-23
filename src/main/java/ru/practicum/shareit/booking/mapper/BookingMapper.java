package ru.practicum.shareit.booking.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@UtilityClass
public final class BookingMapper {
   public static BookingDto modelToDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .item(Item.builder()
                        .id(booking.getItem().getId())
                        .name(booking.getItem().getName())
                        .build())
                .booker(User.builder()
                        .id(booking.getBooker().getId())
                        .build())
                .status(booking.getStatus())
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }

    public static Booking modelFromDto(BookingDto booking, User user, Item item) {
        return Booking.builder()
                .id(booking.getId())
                .booker(user)
                .item(item)
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }
}