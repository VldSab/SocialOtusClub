package ru.saberullin.socialotusclub.role.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.saberullin.socialotusclub.role.model.Role;
import ru.saberullin.socialotusclub.role.model.RoleRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoleRepositoryImpl(@Qualifier("postgresJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Role> findByRole(String role) {
        String findByRoleSql = "SELECT * FROM public.role WHERE role = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(findByRoleSql, new RoleRowMapper(), role));
    }

    @Override
    public List<Role> findAll() {
        return jdbcTemplate.queryForList("SELECT * FROM public.role")
                .stream().map(
                        it -> Role.builder()
                                .id((Long)it.get("id"))
                                .role((String)it.get("role"))
                                .build())
                .toList();
    }

}
