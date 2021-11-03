package playground.repository.user;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import playground.domain.user.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

@Component
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    public UserRepository(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long save(final User user) {
        String saveQuery = "insert into user (email, password, name) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<User> findAllById(final List<Long> userIds) {
        String inSql = String.join(",", Collections.nCopies(userIds.size(), "?"));
        String selectQuery = String.format("select * from user where id in (%s)", inSql);

        return jdbcTemplate.query(selectQuery, (rs, rowNum) ->
                User.builderForDao()
                        .id(rs.getLong("id"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .build(), userIds.toArray());
    }

    public User findById(final Long userId) {
        String selectQuery = "select * from user where id = ?";
        try {
            return jdbcTemplate.queryForObject(selectQuery, (rs, rowNum) ->
                    User.builderForDao()
                            .id(rs.getLong("id"))
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .name(rs.getString("name"))
                            .build(), userId);

        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException(String.format("[%d] 번호에 해당하는 회원이 존재하지 않습니다.", userId));
        }
    }
}
