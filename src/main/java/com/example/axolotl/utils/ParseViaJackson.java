package com.example.axolotl.utils;

import javax.validation.Valid;
import java.lang.annotation.*;


/**
 *  Маркерная аннотация
 *  Дает сигнал, что это поле надо мапить через ObjectMapper
 */


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ParseViaJackson {
}
