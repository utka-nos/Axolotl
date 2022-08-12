package com.example.axolotl.controller;

import com.example.axolotl.domain.Post;
import com.example.axolotl.domain.User;
import com.example.axolotl.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private PostService postService;


    @GetMapping
    public String getProfilePage(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        List<Post> posts = postService.getPostsByAuthorId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "pages/profile_page";
    }

    @GetMapping("/{id}")
    public String GetUserProfilePage(
            @PathVariable("id") User user,
            Model model
    ) {
        List<Post> postsByAuthorId = postService.getPostsByAuthorId(user.getId());
        model.addAttribute("posts", postsByAuthorId);
        model.addAttribute("user", user);
        return "pages/profile_page";
    }

}
