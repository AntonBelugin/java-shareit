package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerIdOrderByStartDesc(Long id);

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Long id, BookingStatus status);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(Long id, LocalDateTime time);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Long id, LocalDateTime time);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long id, LocalDateTime time, LocalDateTime time2);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(Long id, BookingStatus status);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Long id, LocalDateTime time);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Long id, LocalDateTime time);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long id, LocalDateTime time, LocalDateTime time2);
    
    List<Booking> findAllByBookerIdAndItemIdAndEndBefore(Long id, Long itemId, LocalDateTime time);

    List<Booking> findAllByItemIdAndEndBeforeOrderByStartDesc(Long itemId, LocalDateTime time);

    List<Booking> findAllByItemIdAndStartAfterOrderByStartDesc(Long itemId, LocalDateTime time);
}