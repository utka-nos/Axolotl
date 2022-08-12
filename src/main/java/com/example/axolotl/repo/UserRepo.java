package com.example.axolotl.repo;

import com.example.axolotl.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);

    User findByActivationCode(String code);

    List<User> findAllByUsernameOrderByIdDesc(String username);

    List<User> findAllByIdOrderByIdDesc(Long searchText);

    User findUserByUpdateEmailCode(String code);
}
