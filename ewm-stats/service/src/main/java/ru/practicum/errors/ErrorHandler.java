package ru.practicum.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.errors.exceptions.IncorrectDateException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({IncorrectDateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final RuntimeException e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }
}
