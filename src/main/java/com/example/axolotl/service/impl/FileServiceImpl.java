package com.example.axolotl.service.impl;

import com.example.axolotl.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String saveAvatar(MultipartFile multipartFile) throws IOException {
        File file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdir();
        }

        File avasFile = new File(uploadPath + "/avatars");
        if (!avasFile.exists()) {
            avasFile.mkdir();
        }

        String uuid = UUID.randomUUID().toString();
        String filename = uuid + "." + multipartFile.getOriginalFilename();

        multipartFile.transferTo(new File(uploadPath + "/avatars/" + filename));

        return String.format("http://localhost:8080/img/avatars/%s", filename);
    }

    @Override
    public void deleteAvatar(String avatarUrl) {
        String[] urlParts = avatarUrl.split("/");
        String filename = urlParts[urlParts.length - 1];

        File file = new File(uploadPath + "/avatars/" + filename);
        boolean delete = file.delete();
        log.warn("deleted file {{}} = {{}}", filename, delete);
    }

}
