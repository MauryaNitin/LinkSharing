package com.ttn.linksharing.exceptions.exceptionHandlers;

import com.ttn.linksharing.exceptions.GenericExceptionResponse;
import com.ttn.linksharing.exceptions.IncorrectPasswordException;
import com.ttn.linksharing.exceptions.UserAlreadyExistsException;
import com.ttn.linksharing.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.List;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest){
        GenericExceptionResponse genericExceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(genericExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest webRequest){
        GenericExceptionResponse genericExceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(genericExceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest webRequest){
        GenericExceptionResponse genericExceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(genericExceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public final ResponseEntity<Object> handleIncorrectPasswordException(IncorrectPasswordException ex, WebRequest webRequest){
        GenericExceptionResponse genericExceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(genericExceptionResponse, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessageList=ex.getBindingResult().getAllErrors().stream().filter(e->e instanceof FieldError).map((e)->{
            FieldError fieldError = (FieldError) e;
            return messageSource.getMessage(fieldError, null);
        }).collect(Collectors.toList());

        GenericExceptionResponse exceptionResponse = new GenericExceptionResponse(new Date(),"Validation Failed!",errorMessageList.toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
