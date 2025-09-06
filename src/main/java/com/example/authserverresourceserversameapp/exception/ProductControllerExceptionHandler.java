package com.example.authserverresourceserversameapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductControllerExceptionHandler {
    @ExceptionHandler(UserExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(PasswordsDontMatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<ErrorResponse> handlePasswordsDontMatchException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(ProductExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<ErrorResponse> handleProductExistsException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(TypeExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<ErrorResponse> handleTypeExistsException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(BrandExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<ErrorResponse> handleBrandExistsException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<ErrorResponse> handleWrongPasswordException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(403).body(response);
    }

    @ExceptionHandler(TypeNoneCanNotBeUpdatedOrDeletedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<ErrorResponse> handleTypeNotSelectedCanNotBeDeletedException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(BrandNoneCanNotBeUpdatedOrDeletedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<ErrorResponse> handleBrandNotSelectedCanNotBeDeletedException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(409).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            sb.append(errorMessage + "; ");
        });
        return ResponseEntity.status(400).body(new ErrorResponse(sb.toString()));
    }
}
