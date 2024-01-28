package ru.saberullin.socialotusclub.user.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.saberullin.socialotusclub.role.model.Role;
import ru.saberullin.socialotusclub.role.repository.RoleRepository;
import ru.saberullin.socialotusclub.user.model.UserDto;
import ru.saberullin.socialotusclub.user.model.UserEntity;
import ru.saberullin.socialotusclub.user.model.UserEntityRowMapper;

import java.sql.PreparedStatement;
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
    public Optional<UserEntity> findByUsername(String username) {
        String findByNameSql = "SELECT * FROM public.user WHERE username = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(findByNameSql, new UserEntityRowMapper(), username));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> findByNameAndSurname(String firstName, String secondName) {
        String findByNameSql = "SELECT * FROM public.user WHERE name LIKE ? AND surname LIKE ?";
        try {
            return jdbcTemplate.query(findByNameSql, new UserEntityRowMapper(), firstName + "%", secondName + "%");
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        String findByIdSql = "SELECT * FROM public.user WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(findByIdSql, new UserEntityRowMapper(), id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean existsByUsername(String name) {
        String existsByNameSql = "SELECT COUNT(1) FROM public.user WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(existsByNameSql, Integer.class, name);
        return count != null && count > 0;
    }

    @Override
    public Boolean existsById(Long id) {
        String existsByIdSql = "SELECT COUNT(1) FROM public.user WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(existsByIdSql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public List<Role> findRolesByUserName(String username) {
        Optional<UserEntity> user = findByUsername(username);
        if (user.isEmpty()) {
            return List.of();
        }
        long userId = user.get().getId();
        String findRoles = "SELECT role_id FROM public.user_role WHERE user_id = ?";
        Set<Long> roleIds = new HashSet<>(jdbcTemplate.queryForList(findRoles, Long.class, userId));
        List<Role> roles =  roleRepository.findAll();
        return roles.stream().filter(role -> roleIds.contains(role.getId())).toList();
     }

    @Override
    public UserEntity saveUser(UserEntity user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("public")
                .withTableName("user")
                .usingGeneratedKeyColumns("id");
        Long id = simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue();

        String findByIdSql = "SELECT * FROM public.user WHERE id = ?";
        return jdbcTemplate.queryForObject(findByIdSql, new UserEntityRowMapper(), id);
    }

    @Override
    public void saveAllUsers(List<UserEntity> users) {
        String saveBatchSql = "INSERT INTO public.user (username, name, surname) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(saveBatchSql, users, 100,
                (PreparedStatement ps, UserEntity user) -> {
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getName());
                    ps.setString(3, user.getSurname());
                });
    }

    @Override
    public void saveUserRole(UserEntity user, Role role) {
        String saveUserRoleSql = "INSERT INTO public.user_role VALUES (?, ?)";
        jdbcTemplate.update(saveUserRoleSql, user.getId(), role.getId());
    }

    @Override
    public UserEntity updateUser(UserDto userDto) {
        String sqlUpdate = "UPDATE public.user SET username = ?, name = ?, surname = ?, age = ?, sex = ?, interests = ?, city = ? WHERE id = ?";
        jdbcTemplate.update(sqlUpdate,
                userDto.getUsername(),
                userDto.getName(),
                userDto.getSurname(),
                userDto.getAge(),
                userDto.getSex(),
                userDto.getInterests(),
                userDto.getCity(),
                userDto.getId());
        return findByUsername(userDto.getUsername()).get();
    }


}
