package com.example.axolotl.controller;

import com.example.axolotl.domain.User;
import com.example.axolotl.exception.DifferentUsersException;
import com.example.axolotl.exception.EmailNotEmptyException;
import com.example.axolotl.exception.PageNotFoundException;
import com.example.axolotl.exception.UsernameIsNotEmptyException;
import com.example.axolotl.service.ProfileEditService;
import com.example.axolotl.utils.ControllerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/profile")
@Slf4j
public class ProfileEditController {

    @Autowired
    private ProfileEditService profileEditService;

    @GetMapping("/edit")
    public String profileEditPage(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("user", user);
        return "pages/profile_edit_page";
    }

    @PostMapping("/editGeneralInfo")
    public String editProfileInfo(
            @Valid User user,
            BindingResult bindingResult,
            @AuthenticationPrincipal User curUser,
            RedirectAttributes redirectAttributes
    ) {
        //Проверяем на ошибки валидации
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/profile/edit";
        }

        try{
            //если информация не была обновлена, то никаких сообщений выводить не надо
            if(profileEditService.updateGeneralUserInfo(user, curUser)){
                redirectAttributes.addFlashAttribute("message", "Information was updated");
                redirectAttributes.addFlashAttribute("alertStyle", "success");
            }
            else {
                redirectAttributes.addFlashAttribute("message", "Nothing was changed");
                redirectAttributes.addFlashAttribute("alertStyle", "info");
            }
        } catch (UsernameIsNotEmptyException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            redirectAttributes.addFlashAttribute("alertStyle", "danger");
        }
        return "redirect:/profile/edit";
    }

    @GetMapping("/editEmail/{updateEmailCode}")
    public String confirmUpdatingEmail(
            @PathVariable("updateEmailCode") String code,
            @AuthenticationPrincipal User curUser,
            Model model
    ) throws PageNotFoundException {
        try{
            if (profileEditService.confirmUpdatingEmail(code, curUser)) {
                model.addAttribute("message", "You changed email address!");
            }
            else{
                model.addAttribute("message", "Can't update email address because of unknown reasons.");
            }
        } catch (DifferentUsersException ex) {
            model.addAttribute("message", ex.getMessage());
        }
        return "pages/information_page";
    }

    @PostMapping("/editEmail")
    public String editProfileEmail(
            @Valid User user,
            BindingResult bindingResult,
            @AuthenticationPrincipal User curUser,
            RedirectAttributes redirectAttributes
    ) {
        // Ошибки валидации
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/profile/edit";
        }

        try{
            if(profileEditService.editEmail(user, curUser)){
                String message = String.format(
                        "We sent message to %s. Please, follow the instructions in letter and prove your new email.",
                        curUser.getNewEmail()
                );
                redirectAttributes.addFlashAttribute("message", message);
                redirectAttributes.addFlashAttribute("alertStyle", "success");
            }
        } catch (EmailNotEmptyException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            redirectAttributes.addFlashAttribute("alertStyle", "danger");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            redirectAttributes.addFlashAttribute("alertStyle", "danger");
        }

        return "redirect:/profile/edit";
    }

    @PostMapping("/editAvatar")
    public String changeAvatar(
            @RequestParam(name = "avatar", required = false) MultipartFile file,
            @AuthenticationPrincipal User user
    ) throws IOException {
        if (!file.isEmpty()) {
            profileEditService.saveAvatar(file, user);
        }
        return "redirect:/profile/edit";
    }

}
