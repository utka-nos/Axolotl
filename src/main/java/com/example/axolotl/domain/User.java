package com.example.axolotl.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ReflectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;


/**
 * implements UserDetails is using to configure WebSecurityConfig by default.
 */

@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
public class User implements UserDetails{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return active == user.active &&
                id.equals(user.id) &&
                roles.equals(user.roles) &&
                username.equals(user.username) &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                Objects.equals(activationCode, user.activationCode) &&
                Objects.equals(updateEmailCode, user.updateEmailCode) &&
                Objects.equals(newEmail, user.newEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roles, username, password, active, email, activationCode, updateEmailCode, newEmail);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "password can't be empty!")
    private String password;

    private boolean active;

    @NotBlank(message = "Please, enter email")
    @Email(message = "It doesn't look like email")
    private String email;

    private String activationCode;

    private String updateEmailCode;

    private String newEmail;

    private String avatarUrl;

    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public boolean isAdmin() {
        return this.roles.contains(Role.ADMIN);
    }

    public void copyUserInfo(User user) {

        Class<? extends User> aClass = user.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object value = ReflectionUtils.getField(field, user);
            ReflectionUtils.setField(field, this, value);
        }

        /*this.password = user.getPassword();
        this.username = user.getUsername();
        this.email    = user.getEmail();
        this.id       = user.getId();
        this.active   = user.isActive();
        this.roles    = user.getRoles();
        this.newEmail = user.getNewEmail();
        this.updateEmailCode = user.getUpdateEmailCode();
        this.activationCode  = user.getActivationCode();*/
    }
}
