package app.controller.advice;

import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.exceptions.InvalidLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String invalidLoginExceptionHandler(InvalidLoginException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entityNotFoundExceptionHandler(EntityNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DuplicateDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String duplicateDataExceptionHandler(DuplicateDataException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String invalidDataExceptionHandler(InvalidDataException e) {
        return e.getMessage();
    }
}
