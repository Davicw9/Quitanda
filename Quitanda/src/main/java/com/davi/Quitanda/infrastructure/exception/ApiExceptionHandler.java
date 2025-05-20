package com.davi.Quitanda.infrastructure.exception;


import com.davi.Quitanda.fruta.exception.EntityNotFoundException;
import com.davi.Quitanda.fruta.exception.FruitAlreadyExistsException;
import com.davi.Quitanda.fruta.exception.InvalidRegistrationInformationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidRegistrationInformationException.class)
    public ResponseEntity<ErrorMessage> invalidRegistrationInformationException(RuntimeException ex, HttpServletRequest request) {
       log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }


    @ExceptionHandler(FruitAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> UserAlreadyExistsException(RuntimeException ex, HttpServletRequest request) {
       log.error("API Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        BindingResult result = ex.getBindingResult();
        ErrorMessage errorMessage = new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Erro de validação", result);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
