package com.example.axolotl.service.impl;

import com.example.axolotl.domain.Role;
import com.example.axolotl.domain.User;
import com.example.axolotl.dto.ReCaptchaResponseDTO;
import com.example.axolotl.dto.UserRegistrationDTO;
import com.example.axolotl.exception.UserNotAddedException;
import com.example.axolotl.repo.UserRepo;
import com.example.axolotl.service.MailSender;
import com.example.axolotl.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

@Component
public class RegistrationServiceImpl implements RegistrationService {

    private static final String RE_CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String reCaptchaSecret;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private String hostname;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean addNewUser(UserRegistrationDTO user) throws UserNotAddedException {
        User userInDb = userRepo.findByUsername(user.getUsername());

        if (userInDb != null)
            throw new UserNotAddedException("Username is not empty!");
        if(userRepo.findByEmail(user.getEmail()) != null)
            throw new UserNotAddedException("This email is already used! Try to sign in with this email.");

        User newUser = user.getUser();

        newUser.setActive(false);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setActivationCode(UUID.randomUUID().toString());
        newUser.setRoles(Collections.singleton(Role.USER));

        userRepo.save(newUser);

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            String link = String.format("http://%s/activate/%s", hostname, newUser.getActivationCode());
            String messageText = String.format("Please, follow this link (%s) to activate your account", link);
            mailSender.send(user.getEmail(), "Activation", messageText);
        }

        return true;
    }

    @Override
    public User findUserByActivationCode(String code) {
        return userRepo.findByActivationCode(code);
    }

    @Override
    public boolean activateUser(String code, User user) {
        user.setActive(true);
        user.setActivationCode(null);
        userRepo.save(user);
        return false;
    }

    @Override
    public boolean checkReCaptcha(String reCaptchaResponse) {
        if (reCaptchaResponse == null || "".equals(reCaptchaResponse)) {
            return false;
        }

        ReCaptchaResponseDTO response = restTemplate.postForObject(
                String.format(RE_CAPTCHA_URL, reCaptchaSecret, reCaptchaResponse),
                Collections.emptyList(),
                ReCaptchaResponseDTO.class);
        if (response == null) {
            return false;
        }
        return response.isSuccess();
    }
}
