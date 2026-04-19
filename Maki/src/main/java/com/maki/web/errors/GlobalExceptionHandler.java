package com.maki.web.errors;

import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // DTO simple para respuestas de error
    public static class ErrorResponse {
        public String errorCode;
        public String errorTitle;
        public String errorMessage;

        public ErrorResponse(String errorCode, String errorTitle, String errorMessage) {
            this.errorCode = errorCode;
            this.errorTitle = errorTitle;
            this.errorMessage = errorMessage;
        }
    }

    // Página no encontrada
    @ExceptionHandler({ NoHandlerFoundException.class, NoResourceFoundException.class })
    public ResponseEntity<ErrorResponse> handleNotFound(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "404",
                "Página no encontrada",
                "Lo sentimos, la página que buscas no existe o fue movida.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Entidad no encontrada
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                "404",
                "Recurso no encontrado",
                ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Violación de restricción
    @ExceptionHandler(EntityConstraintException.class)
    public ResponseEntity<ErrorResponse> handleConstraint(EntityConstraintException ex) {
        ErrorResponse error = new ErrorResponse(
                "400",
                "Datos inválidos",
                ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Error general
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "500",
                "Error interno",
                "Algo salió mal en el servidor. Por favor intenta más tarde.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}