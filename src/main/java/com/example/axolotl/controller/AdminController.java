package com.example.axolotl.controller;

import com.example.axolotl.domain.Role;
import com.example.axolotl.domain.User;
import com.example.axolotl.exception.EmailNotEmptyException;
import com.example.axolotl.exception.RolesNotSelectedException;
import com.example.axolotl.exception.UsernameIsNotEmptyException;
import com.example.axolotl.service.AdminService;
import com.example.axolotl.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
//Пропускает в методы этого контроллера только пользователей с ролью ADMIN
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public String usersSettings(
            Model model,
            @RequestParam(required = false) String searchField,
            @RequestParam(required = false) String searchText
    ) {
        List<User> users;
        if (searchText != null && !"".equals(searchText)) {
            users = adminService.findAllByFilter(searchField, searchText);
        } else {
            users = adminService.getAllUsers();
        }
        model.addAttribute("searchText", searchText);
        model.addAttribute("searchField", searchField);
        model.addAttribute("users", users);
        return "pages/admin_user_list_page";
    }

    @GetMapping("/users/edit/{user}")
    public String editUserInfo(@PathVariable User user, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", user);
        return "pages/admin_user_edit_page";
    }


    /**
     * update user info method
     * @param user - user to be updated
     * @param form - another fields
     * @param redirectAttributes - redirect attributes
     * @return - page
     */
    @PostMapping("/users/edit/{id}")
    public String updateUserInfo(
            @PathVariable("id") User user,
            @AuthenticationPrincipal User curUser,
            @Valid User validUser,
            BindingResult bindingResult,
            @RequestParam Map<String, String> form,
            RedirectAttributes redirectAttributes
    ) {
        // ошибки валидации сущности user
        // TODO: Создать DTO для этого метода
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/admin/users/edit/" + user.getId();
        }

        // Пробуем обновить информацию о пользователе
        try{
            // обновили
            if (adminService.updateUserInfo(curUser, user, validUser, form)) {
                redirectAttributes.addFlashAttribute("message", "Saved");
                redirectAttributes.addFlashAttribute("messageStyle", "success");
            }
            // не обновили (нечего обновлять - введенные данные не изменились)
            else {
                redirectAttributes.addFlashAttribute("message", "Nothing was changed");
                redirectAttributes.addFlashAttribute("messageStyle", "info");
            }
            // Некорректные данные
        } catch (UsernameIsNotEmptyException | EmailNotEmptyException | RolesNotSelectedException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            redirectAttributes.addFlashAttribute("messageStyle", "danger");
        }
        return "redirect:/admin/users/edit/" + user.getId();
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(
            @PathVariable("id") User userToDelete
    ) {
        adminService.deleteUser(userToDelete);
        return "redirect:/admin/users";
    }

}
