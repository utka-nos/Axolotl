package com.example.axolotl.service.impl;

import com.example.axolotl.domain.Role;
import com.example.axolotl.domain.User;
import com.example.axolotl.exception.EmailNotEmptyException;
import com.example.axolotl.exception.RolesNotSelectedException;
import com.example.axolotl.exception.UsernameIsNotEmptyException;
import com.example.axolotl.repo.UserRepo;
import com.example.axolotl.service.AdminService;
import com.example.axolotl.service.PostService;
import com.example.axolotl.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.SessionRepository;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostService postService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private JdbcIndexedSessionRepository sessionRepository;

    /**
     *
     * @param curUser - текущий пользователь
     * @param pathUser - пользователь, которого хотим отредактировать
     * @param validUser - проверенный на валидность пользователь с новой информацией для редактирования
     * @param form - параметры формы
     * @return - true = обновили информацию, false = не обновили (нет изменений)
     * @throws UsernameIsNotEmptyException - если новый username занят другим пользователем
     * @throws EmailNotEmptyException - если новый email занят другим пользователем
     * @throws RolesNotSelectedException - если не задали новый список ролей
     */
    @Override
    public boolean updateUserInfo(User curUser, User pathUser, User validUser, Map<String, String> form)
            throws UsernameIsNotEmptyException, EmailNotEmptyException, RolesNotSelectedException {

        // если username полученный из формы не равен имени, полученном от user'а из базы данных
        // то меняем имя, проверив на его свободность
        if (!pathUser.getUsername().equals(validUser.getUsername())) {
            User userByUsername = userRepo.findByUsername(validUser.getUsername());
            if (userByUsername != null) {
                throw new UsernameIsNotEmptyException("Username \"" + validUser.getUsername() + "\" is not empty.");
            }
        }

        // Проверяем на уникальность и на изменение Email
        if (!pathUser.getEmail().equals(validUser.getEmail())) {
            User userByEmail = userRepo.findByEmail(validUser.getEmail());
            if (userByEmail != null) {
                throw new EmailNotEmptyException("Email " + validUser.getEmail() + " is already in use!");
            }
        }

        // Вытаским роли из формы и проверяем на наличие какой-либо роли
        Set<Role> roles = new HashSet<>();
        Arrays.stream(Role.values()).peek(role -> {
            if (form.containsKey(role.getAuthority()) && form.get(role.getAuthority()).equals("on")) roles.add(role);
        }).collect(Collectors.toSet());
        if (roles.isEmpty()) throw new RolesNotSelectedException("Can't be no role. Please, select USER role at least.");

        // копируем чтобы сравнить на изменения
        User oldUser = new User();
        oldUser.copyUserInfo(pathUser);

        // задаем новые параметры
        pathUser.setRoles(roles);
        pathUser.setActive(form.containsKey("isActive") && form.get("isActive").equals("on"));
        pathUser.setUsername(validUser.getUsername());
        pathUser.setEmail(validUser.getEmail());

        // если ничего не изменилось не пересаписываем сессии
        if (pathUser.equals(oldUser)) {
            return false;
        }
        
        userRepo.save(pathUser);

        // если редактировали себя, то обновляем инфу о себе
        if (pathUser.getId().equals(curUser.getId())) {
            curUser.copyUserInfo(pathUser);
            SessionUtils.updateSessionSecurityContext(httpSession);
        }
        // Если не себя, то обрываем сессию тому, кого отредактировали
        else {
            SessionUtils.deleteSessionByUsername(oldUser.getUsername(), sessionRepository);
        }

        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<User> findAllByFilter(String searchField, String searchText) {
        switch (searchField) {
            case "username":
                return userRepo.findAllByUsernameOrderByIdDesc(searchText);
            case "id":
                return userRepo.findAllByIdOrderByIdDesc(Long.valueOf(searchText));
        }
        return userRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public void deleteUser(User userToDelete) {
        postService.deleteAllByAuthorId(userToDelete.getId());
        userRepo.deleteById(userToDelete.getId());
    }
}
