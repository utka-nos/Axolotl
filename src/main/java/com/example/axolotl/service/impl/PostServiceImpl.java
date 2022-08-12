package com.example.axolotl.service.impl;

import com.example.axolotl.domain.Post;
import com.example.axolotl.domain.User;
import com.example.axolotl.repo.PostRepo;
import com.example.axolotl.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll(Sort.by(Sort.Direction.DESC, "time"));
    }

    @Override
    public Boolean addNewPost(Post post, MultipartFile file, User curUser) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            setFile(post, file);
        }
        post.setAuthor(curUser);
        postRepo.save(post);
        return true;
    }

    @Override
    public List<Post> getAllFilteredPosts(String tagFilter) {
        return postRepo.findAllByTag(tagFilter);
    }

    private void setFile(Post post, MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resFilename = uuidFile + "." + file.getOriginalFilename();

        //загружаем файл
        file.transferTo(new File(uploadPath + "/" + resFilename));

        post.setFilename(resFilename);
    }

    @Override
    public void deleteAllByAuthorId(Long id) {
        postRepo.deletePostsByAuthorId(id);
    }

    @Override
    public List<Post> getPostsByAuthorId(Long id) {
        return postRepo.findAllByAuthorId(id).orElse(new ArrayList<>());
    }
}
