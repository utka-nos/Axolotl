package com.example.axolotl.controller;

import com.example.axolotl.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String main(
            @AuthenticationPrincipal User user,
            Model model) {
        model.addAttribute("user", user);
        return "main";
    }

    @GetMapping("/")
    public String rootPage(Model model) {
        return "index";
    }

}
