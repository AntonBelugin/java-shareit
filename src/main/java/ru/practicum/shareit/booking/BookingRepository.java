package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    default Booking getBookingById(long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Бронирование с id " + id + " не существует"));
    }

    List<Booking> findByBookerIdOrderByStartDesc(Long id);

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Long id, BookingStatus status);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(Long id, LocalDateTime time);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Long id, LocalDateTime time);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long id,
                                                                             LocalDateTime time, LocalDateTime time2);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(Long id, BookingStatus status);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Long id, LocalDateTime time);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Long id, LocalDateTime time);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long id,
                                                                                LocalDateTime time, LocalDateTime time2);

    Boolean existsAllByBookerIdAndItemIdAndEndBefore(Long id, Long itemId, LocalDateTime time);

    List<Booking> findAllByItemIdAndEndBeforeOrderByStartDesc(Long itemId, LocalDateTime time);

    List<Booking> findAllByItemIdAndStartAfterOrderByStartDesc(Long itemId, LocalDateTime time);
}