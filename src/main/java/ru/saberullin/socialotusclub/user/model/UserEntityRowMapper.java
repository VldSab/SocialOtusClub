package ru.saberullin.socialotusclub.user.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserEntityRowMapper implements RowMapper<UserEntity> {
    @Override
    public UserEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return UserEntity.builder()
                .id(resultSet.getLong("id"))
                .username("username")
                .name(resultSet.getString("name"))
                .surname(resultSet.getString("surname"))
                .age(resultSet.getInt("age"))
                .sex(resultSet.getString("sex"))
                .interests(resultSet.getString("interests"))
                .city(resultSet.getString("city"))
                .password(resultSet.getString("password"))
                .build();
    }
}
