package ru.saberullin.socialotusclub.post;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;

    public PostRepositoryImpl(@Qualifier("postgresJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Post savePost(Post post) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("public")
                .withTableName("post")
                .usingGeneratedKeyColumns("id");
        Long id = simpleJdbcInsert.executeAndReturnKey(post.toMap()).longValue();

        String findByIdSql = "SELECT * FROM public.post WHERE id = ?";
        return jdbcTemplate.queryForObject(findByIdSql, new PostMapper(), id);
    }

    @Override
    public boolean existsById(Long id) {
        String sqlExistsById = "SELECT COUNT(1) from public.post WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sqlExistsById, Integer.class, id);
        return count != null && count > 0;
    }
}
