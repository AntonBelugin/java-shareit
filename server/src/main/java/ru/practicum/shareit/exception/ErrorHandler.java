package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final ValidationException e) {
        log.error("Введены ошибочные параметры {}", e.getMessage(), e);
        return new ErrorResponse(
                "Ошибка с параметрами",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(final NotFoundException e) {
        log.error("Введены несуществующие данные {}", e.getMessage(), e);
        return new ErrorResponse(
                "Неправильный ввод данных",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(final RuntimeException e) {
        log.error("Ошибка сервера {}", e.getMessage(), e);
        return new ErrorResponse(
                "Внутренняя ошибка сервера",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle(final ConflictDataException e) {
        log.error("Введены неправильные данные {}", e.getMessage(), e);
        return new ErrorResponse(
                "Неправильный ввод данных",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handle(final AccessDeniedException e) {
        log.error("Введены параметры нарущающие права доступа {}", e.getMessage(), e);
        return new ErrorResponse(
                "Доступ не разрешен",
                e.getMessage()
        );
    }
}
