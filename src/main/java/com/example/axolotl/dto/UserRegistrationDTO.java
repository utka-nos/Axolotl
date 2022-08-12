package com.example.axolotl.dto;

import com.example.axolotl.domain.User;
import com.example.axolotl.validation.FieldEquals;
import com.example.axolotl.validation.SuccessRecaptcha;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegistrationDTO implements Serializable {

    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Please, enter email!")
    @Email(message = "Don't looks like email...")
    private String email;

    @JsonProperty("g-recaptcha-response")
    @NotBlank(message = "please, fill the recaptcha!")
    @SuccessRecaptcha(message = "problems with recaptcha")
    private String recaptcha;

    @FieldEquals(message = "Passwords are not the same")
    @NotBlank(message = "Please, enter password")
    private String password;

    @FieldEquals(message = "Passwords are not the same")
    @NotBlank(message = "Passwords don't match")
    private String passwordConfirm;

    public User getUser() {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
