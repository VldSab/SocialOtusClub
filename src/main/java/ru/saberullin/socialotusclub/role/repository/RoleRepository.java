package ru.saberullin.socialotusclub.role.repository;

import ru.saberullin.socialotusclub.role.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByRole(String role);
    List<Role> findAll();
}
