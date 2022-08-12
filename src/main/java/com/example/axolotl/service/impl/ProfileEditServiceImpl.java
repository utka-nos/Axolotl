package com.example.axolotl.service.impl;

import com.example.axolotl.domain.User;
import com.example.axolotl.exception.DifferentUsersException;
import com.example.axolotl.exception.EmailNotEmptyException;
import com.example.axolotl.exception.PageNotFoundException;
import com.example.axolotl.exception.UsernameIsNotEmptyException;
import com.example.axolotl.repo.UserRepo;
import com.example.axolotl.service.FileService;
import com.example.axolotl.service.MailSender;
import com.example.axolotl.service.ProfileEditService;
import com.example.axolotl.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProfileEditServiceImpl implements ProfileEditService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private String ipAddress;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private HttpSession httpSession;

    /*@Autowired
    private JdbcIndexedSessionRepository sessionRepository;*/

    @Autowired
    private FileService fileService;

    @Override
    public boolean updateGeneralUserInfo(User user, User curUser) throws UsernameIsNotEmptyException {
        // если основные поля не поменялись, то ничего не выводим
        if (user.getUsername().equals(curUser.getUsername())) return false;
        // пытаемся найти в бд пользователя с таким же ником
        User byUsername = userRepo.findByUsername(user.getUsername());
        // если находим, то говорим, что уже есть такой ник
        if (byUsername != null) throw new UsernameIsNotEmptyException("Username is not empty!");
        //пока только username меняем, но потом здесь всю основную информацию менять будем
        curUser.setUsername(user.getUsername());

        // Обновляем сессию в базе данных (перезаписываем атрибут)
        SessionUtils.updateSessionSecurityContext(httpSession);

        userRepo.save(curUser);
        return true;
    }

    @Override
    public boolean editEmail(User user, User curUser) throws EmailNotEmptyException {
        if (user.getEmail().equals(curUser.getEmail())) return false;
        if (userRepo.findByEmail(user.getEmail()) != null) throw new EmailNotEmptyException("Email is already in use!");

        String updateEmailCode = UUID.randomUUID().toString();

        curUser.setUpdateEmailCode(updateEmailCode);
        curUser.setNewEmail(user.getEmail());

        String emailTo = user.getEmail();
        String subject = "Confirm";
        String link    = String.format("http://%s/profile/editEmail/%s", ipAddress, updateEmailCode);
        String text    = String.format("Please, follow this link (%s) to confirm new data", link);
        mailSender.send(emailTo, subject, text);

        userRepo.save(curUser);
        return true;
    }

    @Override
    public boolean confirmUpdatingEmail(String code, User curUser) throws PageNotFoundException, DifferentUsersException {
        User userFromDb = userRepo.findUserByUpdateEmailCode(code);
        if(userFromDb == null)
            throw new PageNotFoundException("No such page!");

        if(!userFromDb.getId().equals(curUser.getId()))
            throw new DifferentUsersException("Please, log in to the account where you want to change the email");

        String newEmail = curUser.getNewEmail();

        curUser.setUpdateEmailCode(null);
        curUser.setEmail(newEmail);
        curUser.setNewEmail(null);

        userRepo.save(curUser);
        return true;
    }

    @Override
    @Transactional
    public void saveAvatar(MultipartFile file, User user) throws IOException {
        String avatarUrl = fileService.saveAvatar(file);
        if (!user.getAvatarUrl().equals(avatarUrl)) {
            fileService.deleteAvatar(user.getAvatarUrl());
        }
        user.setAvatarUrl(avatarUrl);
        userRepo.save(user);
        SessionUtils.updateSessionSecurityContext(httpSession);
    }
}
