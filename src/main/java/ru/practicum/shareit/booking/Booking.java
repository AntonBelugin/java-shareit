package ru.practicum.shareit.booking;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 *  TODO Sprint add-bookings.
 */

@Entity
@Table(name = "bookings")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    @JoinColumn(name = "item_id")
    Item item;
    @OneToOne
    @JoinColumn(name = "booker")
    User booker;
    @Enumerated(EnumType.STRING)
    BookingStatus status;
    @NotNull
    LocalDateTime start;
    @NotNull
    @Column(name = "finish")
    LocalDateTime end;
}
