package com.maki.web.errors;

import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Página no encontrada //
    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public String handleNotFound(Exception ex, Model model) {
        model.addAttribute("errorCode", "404");
        model.addAttribute("errorTitle", "Página no encontrada");
        model.addAttribute("errorMessage", "Lo sentimos, la página que buscas no existe o fue movida.");
        model.addAttribute("errorIcon");
        return "error-page";
    }

    // Entidad no encontrada en base de datos //
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex, Model model) {
        model.addAttribute("errorCode", "404");
        model.addAttribute("errorTitle", "Recurso no encontrado");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorIcon");
        return "error-page";
    }

    // Violación de restricción //
    @ExceptionHandler(EntityConstraintException.class)
    public String handleConstraint(EntityConstraintException ex, Model model) {
        model.addAttribute("errorCode", "400");
        model.addAttribute("errorTitle", "Datos inválidos");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorIcon");
        return "error-page";
    }

    //Cualquier otro error inesperado//
    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("errorCode", "500");
        model.addAttribute("errorTitle", "Error interno");
        model.addAttribute("errorMessage", "Algo salió mal en el servidor. Por favor intenta más tarde.");
        model.addAttribute("errorIcon");
        return "error-page";
    }
}