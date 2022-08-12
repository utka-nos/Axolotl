package com.example.axolotl.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {

    // Возвращает ошибки валидации в виде Map<String, String>,
    // где key - "fieldNameError", value - default message (сообщения, которые указаны в аннотация класса модели)
    public static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> fieldErrorMapCollector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage,
                (existing, replacement) -> existing
        );
        return bindingResult.getFieldErrors().stream().collect(fieldErrorMapCollector);
    }

}
