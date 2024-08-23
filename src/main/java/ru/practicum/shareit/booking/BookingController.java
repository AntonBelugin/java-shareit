package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

/**
 *  TODO Sprint add-bookings.
 */

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") long userId, @Valid @RequestBody BookingDto booking) {
        return bookingService.add(userId, booking);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approved(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long bookingId,
                               @RequestParam(defaultValue = "") Boolean approved) {
        return bookingService.approved(userId, bookingId, approved);
    }

    @GetMapping("/{id}")
    public BookingDto findById(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long id) {
        return bookingService.findById(userId, id);
    }

    @GetMapping
    public List<BookingDto> getAllByBooker(@RequestHeader("X-Sharer-User-Id") long bookerId,
                                           @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllByBooker(bookerId, state);
    }

    @GetMapping("owner/")
    public List<BookingDto> getAllByOwner(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                           @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllByOwner(ownerId, state);
    }
}
