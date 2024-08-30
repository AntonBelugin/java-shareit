package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    static final String HEADER_USER = "X-Sharer-User-Id";

    @PostMapping
    public BookingDtoResponse create(@RequestHeader(HEADER_USER) long userId, @Valid @RequestBody BookingDtoRequest booking) {
        return bookingService.add(userId, booking);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResponse approved(@RequestHeader(HEADER_USER) long userId, @PathVariable long bookingId,
                                       @RequestParam(defaultValue = "") Boolean approved) {
        return bookingService.approved(userId, bookingId, approved);
    }

    @GetMapping("/{id}")
    public BookingDtoResponse findById(@RequestHeader(HEADER_USER) long userId, @PathVariable long id) {
        return bookingService.findById(userId, id);
    }

    @GetMapping
    public List<BookingDtoResponse> getAllByBooker(@RequestHeader(HEADER_USER) long bookerId,
                                                  @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllByBooker(bookerId, state);
    }

    @GetMapping("owner/")
    public List<BookingDtoResponse> getAllByOwner(@RequestHeader(HEADER_USER) long ownerId,
                                                 @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllByOwner(ownerId, state);
    }
}
