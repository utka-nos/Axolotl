package com.example.axolotl.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String saveAvatar(MultipartFile multipartFile) throws IOException;

    void deleteAvatar(String avatarUrl);

}
