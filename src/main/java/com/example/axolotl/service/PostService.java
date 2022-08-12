package com.example.axolotl.service;

import com.example.axolotl.domain.Post;
import com.example.axolotl.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> getAllPosts();

    Boolean addNewPost(Post post, MultipartFile file, User curUser) throws IOException;

    List<Post> getAllFilteredPosts(String tagFilter);

    void deleteAllByAuthorId(Long id);

    List<Post> getPostsByAuthorId(Long id);
}

