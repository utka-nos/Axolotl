package com.example.axolotl.service;

import com.example.axolotl.domain.User;
import com.example.axolotl.exception.EmailNotEmptyException;
import com.example.axolotl.exception.RolesNotSelectedException;
import com.example.axolotl.exception.UsernameIsNotEmptyException;

import java.util.List;
import java.util.Map;

public interface AdminService {

    boolean updateUserInfo(User curUser, User pathUser, User validUser, Map<String, String> form) throws UsernameIsNotEmptyException, EmailNotEmptyException, RolesNotSelectedException;

    List<User> getAllUsers();

    List<User> findAllByFilter(String searchField, String searchText);

    void deleteUser(User userToDelete);
}
