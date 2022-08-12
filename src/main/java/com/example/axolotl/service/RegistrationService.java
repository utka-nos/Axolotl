package com.example.axolotl.service;

import com.example.axolotl.domain.User;
import com.example.axolotl.dto.UserRegistrationDTO;
import com.example.axolotl.exception.UserNotAddedException;

public interface RegistrationService {

    boolean addNewUser(UserRegistrationDTO user) throws UserNotAddedException;

    User findUserByActivationCode(String code);

    boolean activateUser(String code, User user);

    boolean checkReCaptcha(String reCaptchaResponse);
}
