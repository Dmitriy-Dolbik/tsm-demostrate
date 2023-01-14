package ru.karod.tsm.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.karod.tsm.exceptions.AuthException;
import ru.karod.tsm.exceptions.EmailSendingException;
import ru.karod.tsm.exceptions.ErrorResponse;
import ru.karod.tsm.exceptions.InvalidRequestValuesException;
import ru.karod.tsm.exceptions.NotFoundException;
import ru.karod.tsm.exceptions.ReadingFileException;

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
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleEmailSendingException(
            EmailSendingException exc) {
        ErrorResponse response = new ErrorResponse(exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleReadingFileException(
            ReadingFileException exc) {
        ErrorResponse response = new ErrorResponse(exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
