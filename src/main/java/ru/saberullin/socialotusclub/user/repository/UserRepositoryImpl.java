package ru.saberullin.socialotusclub.user.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.saberullin.socialotusclub.role.model.Role;
import ru.saberullin.socialotusclub.role.repository.RoleRepository;
import ru.saberullin.socialotusclub.user.model.UserEntity;
import ru.saberullin.socialotusclub.user.model.UserEntityRowMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RoleRepository roleRepository;

    public UserRepositoryImpl(@Qualifier("postgresJdbcTemplate") JdbcTemplate jdbcTemplate, RoleRepository roleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<UserEntity> findByName(String name) {
        String findByNameSql = "SELECT * FROM public.user WHERE name = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(findByNameSql, new UserEntityRowMapper(), name));
    }

    @Override
    public Boolean existsByName(String name) {
        String existsByNameSql = "SELECT COUNT(1) FROM public.user WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(existsByNameSql, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public List<Role> findRolesByUserName(String name) {
        Optional<UserEntity> user = findByName(name);
        if (user.isEmpty()) {
            //TODO
            return List.of();
        }
        long userId = user.get().getId();
        String findRoles = "SELECT role_id FROM public.user_role WHERE user_id = ?";
        Set<Long> roleIds = new HashSet<>(jdbcTemplate.queryForList(findRoles, Long.class, userId));
        List<Role> roles =  roleRepository.findAll();
        return roles.stream().filter(role -> roleIds.contains(role.getId())).toList();
     }
}
