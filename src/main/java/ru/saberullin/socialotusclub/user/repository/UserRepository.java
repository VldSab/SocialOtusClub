package ru.saberullin.socialotusclub.user.repository;

import ru.saberullin.socialotusclub.role.model.Role;
import ru.saberullin.socialotusclub.user.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByName(String name);
    Boolean existsByName(String name);
    List<Role> findRolesByUserName(String name);
}
