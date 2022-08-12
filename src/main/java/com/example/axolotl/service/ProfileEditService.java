package com.example.axolotl.service;

import com.example.axolotl.domain.User;
import com.example.axolotl.exception.DifferentUsersException;
import com.example.axolotl.exception.EmailNotEmptyException;
import com.example.axolotl.exception.PageNotFoundException;
import com.example.axolotl.exception.UsernameIsNotEmptyException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileEditService {
    boolean updateGeneralUserInfo(User user, User curUser) throws UsernameIsNotEmptyException;

    boolean editEmail(User user, User curUser) throws EmailNotEmptyException;

    boolean confirmUpdatingEmail(String code, User curUser) throws PageNotFoundException, DifferentUsersException;

    void saveAvatar(MultipartFile file, User user) throws IOException;
}
