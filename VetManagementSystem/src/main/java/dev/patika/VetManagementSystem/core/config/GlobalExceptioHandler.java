package dev.patika.VetManagementSystem.core.config;

import dev.patika.VetManagementSystem.core.exception.NotFoundException;
import dev.patika.VetManagementSystem.core.result.Result;
import dev.patika.VetManagementSystem.core.result.ResultData;
import dev.patika.VetManagementSystem.core.utiles.ResultHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptioHandler {

    // NotFoundException istisnasını ele alır
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result> handleNotFoundException(NotFoundException e){
        return new ResponseEntity<>(ResultHelper.notFound(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    // MethodArgumentNotValidException istisnasını ele alır
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultData<List<String>>> handleValidationErrors(MethodArgumentNotValidException e){
        // Hata listesini alır ve her bir hatayı bir dizeye dönüştürür
        List<String> validationErrorList = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        // Doğrulama hatalarıyla birlikte bir sonuç döndürür
        return new ResponseEntity<>(ResultHelper.validateError(validationErrorList), HttpStatus.BAD_REQUEST);
    }
}
