package com.example.axolotl.controller;

import com.example.axolotl.domain.Post;
import com.example.axolotl.domain.User;
import com.example.axolotl.service.PostService;
import com.example.axolotl.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class NewsController {

    @Autowired
    private PostService postService;

    @GetMapping("/news")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "pages/news_page";
    }

    @PostMapping("/news")
    public String addNewPost(
            @Valid Post post,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal User curUser,
            @RequestParam("image") MultipartFile file
    ) throws IOException {
        Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
        } else {
            postService.addNewPost(post, file, curUser);
        }
        return "redirect:/news";
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException(
            Exception ex,
            RedirectAttributes redirectAttributes
    ){
        redirectAttributes.addFlashAttribute("message", ex.getMessage());
        return "redirect:/news";
    }


    @GetMapping("/filteredNews")
    public String getPostsByFilter(Model model, String tagFilter) {
        if (tagFilter == null || tagFilter.isEmpty()) return "redirect:http://localhost:8080/news";
        model.addAttribute("posts", postService.getAllFilteredPosts(tagFilter));
        model.addAttribute("tagFilter", tagFilter);
        return "pages/news_page";
    }
}
