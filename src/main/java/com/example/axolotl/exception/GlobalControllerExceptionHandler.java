package com.example.axolotl.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler({PageNotFoundException.class, HttpClientErrorException.NotFound.class})
    public String handlePageNotFoundException(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "pages/not_found_page";
    }




}
