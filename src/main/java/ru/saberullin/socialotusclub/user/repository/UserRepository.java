package ru.saberullin.socialotusclub.user.repository;

import ru.saberullin.socialotusclub.role.model.Role;
import ru.saberullin.socialotusclub.user.model.UserDto;
import ru.saberullin.socialotusclub.user.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findByNameAndSurname(String firstName, String secondName);
    Optional<UserEntity> findById(Long id);
    Boolean existsByUsername(String name);
    Boolean existsById(Long id);
    List<Role> findRolesByUserName(String name);
    UserEntity saveUser(UserEntity user);
    void saveUserRole(UserEntity user, Role role);
    UserEntity updateUser(UserDto userDto);
}
