package com.example.axolotl.config.resolvers;

import com.example.axolotl.dto.UserRegistrationDTO;
import com.example.axolotl.utils.ParseViaJackson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.util.Map;

public class UserRegistrationDTOArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Annotation[] methodAnnotations = parameter.getMethodAnnotations();
        for (Annotation annotation : methodAnnotations) {
            if (annotation.annotationType().equals(ParseViaJackson.class)) return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {

        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        StringBuilder paramStr = new StringBuilder();
        paramStr.append("{");
        parameterMap.forEach((key, value) -> {
            paramStr.append("\"").append(key).append("\"").append(" : ").append("\"").append(value[0]).append("\", ");
        });
        paramStr.delete(paramStr.length() - 2, paramStr.length());
        paramStr.append("}");
        String jsonBody = paramStr.toString();

        ObjectMapper objectMapper = new ObjectMapper();
        UserRegistrationDTO userRegistrationDTO = objectMapper.readValue(jsonBody, UserRegistrationDTO.class);

        return userRegistrationDTO;
    }


}
