package ru.practicum.shareit.booking.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.validation.StartBeforeEndDateValid;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@StartBeforeEndDateValid
public class BookItemRequestDto {
	private long itemId;
	@FutureOrPresent
	@NotNull
	private LocalDateTime start;
	@Future
	@NotNull
	private LocalDateTime end;
}
