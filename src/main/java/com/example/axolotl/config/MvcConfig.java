package com.example.axolotl.config;

import com.example.axolotl.config.resolvers.UserRegistrationDTOArgumentResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    //В этом методе можно раздавать статику, не создавая контроллеров
    @Override
    public void addViewControllers(ViewControllerRegistry reg) {
        /*Тут мы говорим, что при запросе по адресу /login мы будем показывать страничку login_macro.ftlh.
        То есть не надо создавать отдельного контроллера.
        Мы могли бы создать отдельный метод @GetMapping в контроллере, но можно так сэкономить место.
        Важно отметить, что /login POST - это отдельный зарезервированный спринг секьюрити мэпинг.*/
        reg.addViewController("/login").setViewName("login");
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:///" + uploadPath + "/");
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserRegistrationDTOArgumentResolver());
    }


}
