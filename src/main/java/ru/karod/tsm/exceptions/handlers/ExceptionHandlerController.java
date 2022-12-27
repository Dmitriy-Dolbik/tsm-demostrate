package ru.karod.tsm.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.karod.tsm.exceptions.AuthException;
import ru.karod.tsm.exceptions.ErrorResponse;
import ru.karod.tsm.exceptions.InvalidRequestValuesException;
import ru.karod.tsm.exceptions.NotFoundException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleAuthException(
            AuthException exc) {
        ErrorResponse response = new ErrorResponse(exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleInvalidRequestValuesException(
            InvalidRequestValuesException exc) {
        ErrorResponse response = new ErrorResponse(exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException exc) {
        ErrorResponse response = new ErrorResponse(exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
