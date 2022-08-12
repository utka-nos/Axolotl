package com.example.axolotl.exception;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice()
public class NewsControllerAdvice {

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public String maxFileSizeException(RedirectAttributes attributes, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("imageException", "File is too big!");
        attributes.addFlashAttribute("errors", errors);
        return "redirect:/news";
    }

}
