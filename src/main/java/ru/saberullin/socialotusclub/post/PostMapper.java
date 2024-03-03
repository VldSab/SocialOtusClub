package ru.saberullin.socialotusclub.post;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Post.builder()
                .id(rs.getLong("id"))
                .ownerId(rs.getLong("owner_id"))
                .payload(rs.getString("payload"))
                .build();
    }
}
