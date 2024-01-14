package ru.saberullin.socialotusclub.user.repository;

import org.apache.catalina.User;
import ru.saberullin.socialotusclub.role.model.Role;
import ru.saberullin.socialotusclub.user.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findById(Long id);
    Boolean existsByName(String name);
    Boolean existsById(Long id);
    List<Role> findRolesByUserName(String name);
    UserEntity saveUser(UserEntity user);
    void saveUserRole(UserEntity user, Role role);
}
