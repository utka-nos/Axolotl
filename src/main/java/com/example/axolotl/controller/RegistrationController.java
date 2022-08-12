package com.example.axolotl.controller;

import com.example.axolotl.domain.User;
import com.example.axolotl.dto.UserRegistrationDTO;
import com.example.axolotl.exception.UserNotAddedException;
import com.example.axolotl.service.RegistrationService;
import com.example.axolotl.utils.ControllerUtils;
import com.example.axolotl.utils.ParseViaJackson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private SmartValidator validator;

    @PostMapping(value = "/registration", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @ParseViaJackson
    public String registration(
            UserRegistrationDTO user,
            RedirectAttributes redirectAttributes
            ) {
        Map<String, String> errors;
        redirectAttributes.addFlashAttribute("user", user);

        // Validate our user because of custom argumentResolver and @Valid can't work together
        DataBinder dataBinder = new DataBinder(user);
        dataBinder.setValidator(validator);
        dataBinder.validate();

        BindingResult bindingResult = dataBinder.getBindingResult();

        // Validation errors
        if (bindingResult.hasErrors()) {
            errors = ControllerUtils.getErrors(bindingResult);
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/registration";
        }

        try{
            if(registrationService.addNewUser(user)) {
                redirectAttributes.addFlashAttribute("message",
                        "To confirm your email and get access to your account, please, follow the link in email!");
                redirectAttributes.addFlashAttribute("textStyle", "success");
            }
        } catch (UserNotAddedException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            redirectAttributes.addFlashAttribute("textStyle", "danger");
        }
        return "redirect:/registration";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        User user = registrationService.findUserByActivationCode(code);
        if (user != null) {
            registrationService.activateUser(code, user);
            model.addAttribute("message", "Your account have been activated!");
        }
        else {
            model.addAttribute("message", "This url is invalid, or you have invalid activation code");
        }
        return "pages/confirm_activation_page";
    }

    @GetMapping("/registration")
    public String registrationPage(){
        return "pages/registration_page";
    }

}
