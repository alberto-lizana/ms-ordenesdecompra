package com.albertolizana.ms_ordenes_de_compra.exception;

import java.time.LocalDateTime;
import  java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.albertolizana.ms_ordenes_de_compra.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalState(IllegalStateException ex) {
        ErrorResponseDto errResponse = ErrorResponseDto.builder()
                .mensaje("Operación no permitida")
                .error(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errResponse);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException ex){
        List<String> err = ex
                            .getBindingResult()
                            .getFieldErrors()
                            .stream()
                            .map(e -> e.getField() + ": " + e.getDefaultMessage())
                            .toList();

        ErrorResponseDto errResponse = ErrorResponseDto
                                    .builder()
                                        .mensaje("Error de validación")
                                        .error(err.toString())
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .timestamp(LocalDateTime.now())
                                    .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<ErrorResponseDto> handleStokInsuficiente(StockInsuficienteException ex){
        ErrorResponseDto errResponse = ErrorResponseDto
                                        .builder()
                                            .mensaje("Stock insuficiente")
                                            .error(ex.getMessage())
                                            .status(HttpStatus.CONFLICT.value())
                                            .timestamp(LocalDateTime.now())
                                        .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errResponse);                       
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex){
        ErrorResponseDto errResponse = ErrorResponseDto
                                        .builder()
                                            .mensaje("Recurso no encontrado")
                                            .error(ex.getMessage())
                                            .status(HttpStatus.NOT_FOUND.value())
                                            .timestamp(LocalDateTime.now())
                                        .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);        
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleNotFound(NoHandlerFoundException ex) {

        ErrorResponseDto errResponse = ErrorResponseDto
                                        .builder()
                                            .mensaje("Recurso no encontrado")
                                            .error(ex.getMessage())
                                            .status(HttpStatus.NOT_FOUND.value())
                                            .timestamp(LocalDateTime.now())
                                        .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralError(Exception ex) {

        ErrorResponseDto errResponse = ErrorResponseDto
                                        .builder()
                                            .mensaje("Error interno del servidor")
                                            .error(ex.getMessage())
                                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                            .timestamp(LocalDateTime.now())
                                        .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }    
}
