package ru.practicum.shareit.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.model.Request;

import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Page<Request> findBySearcherIdOrderByCreatedDesc(Long userId, Pageable pageable);

    Optional<Request> findById(Long id);

    default Request getItemRequestBy(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Запрос с id = %d не найден", id)));
    }
}