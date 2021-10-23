package pt.sharespot.services.lgt92gpssensorgateway.infrastructure.endpoint.rest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.sharespot.services.lgt92gpssensorgateway.model.exceptions.DomainException;
import pt.sharespot.services.lgt92gpssensorgateway.model.exceptions.NotFoundException;
import pt.sharespot.services.lgt92gpssensorgateway.model.exceptions.NotUniqueException;
import pt.sharespot.services.lgt92gpssensorgateway.model.exceptions.NotValidException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(
            DomainException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getError(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = NotValidException.class)
    protected ResponseEntity<Object> handleBadRequest(
            DomainException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getError(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = NotUniqueException.class)
    protected ResponseEntity<Object> handleConflictRequest(
            DomainException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getError(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
