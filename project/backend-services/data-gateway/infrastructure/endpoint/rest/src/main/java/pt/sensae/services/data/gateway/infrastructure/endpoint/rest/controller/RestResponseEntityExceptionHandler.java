package pt.sensae.services.data.gateway.infrastructure.endpoint.rest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.sensae.services.data.gateway.application.NotValidException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotValidException.class)
    protected ResponseEntity<Object> handleNotFound(
            NotValidException ex, WebRequest request) {
        return handleExceptionInternal(ex.getException(), ex.getError(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
