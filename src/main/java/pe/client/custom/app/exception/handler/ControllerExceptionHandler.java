package pe.client.custom.app.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.client.custom.app.exception.BadRequestException;
import pe.client.custom.app.exception.InternalServerException;
import pe.client.custom.app.exception.UnavailableException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage badRequestException(BadRequestException ex) {
        return ErrorMessage.builder()
            .statusCode(HttpStatus.BAD_REQUEST)
            .userMessage(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage internalServerException(InternalServerException ex) {
        return ErrorMessage.builder()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
            .userMessage(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
    }

    @ExceptionHandler(UnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorMessage unavaiableException(UnavailableException ex) {
        return ErrorMessage.builder()
            .statusCode(HttpStatus.SERVICE_UNAVAILABLE)
            .userMessage(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
    }
}
