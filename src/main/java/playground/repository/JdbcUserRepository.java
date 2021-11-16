package playground.repository;

import learning.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static playground.repository.SQLRepository.FIND_USER_BY_ID;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject(FIND_USER_BY_ID, userRowMapper(), id);
    }

    @Override
    public Long save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());
        parameters.put("insert_date", LocalDateTime.now());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return key.longValue();
    }

    public RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> User.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .insertDate(rs.getObject("insert_date", LocalDateTime.class))
                .updateDate(rs.getObject("update_date", LocalDateTime.class))
                .build();
    }
}
