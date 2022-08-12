package com.example.axolotl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public String getHostname() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            String hostAddress = ia.getHostAddress();
            return String.format("%s:8080", hostAddress);
        } catch (UnknownHostException ex) {
            return "127.0.0.1:8080";
        }
    }

}
