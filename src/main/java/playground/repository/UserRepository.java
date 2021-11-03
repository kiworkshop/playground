package playground.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import playground.domain.User;

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
        String selectQuery = String.format("select * from user where id in(%s)", inSql);

        return jdbcTemplate.query(selectQuery, (rs, rowNum) ->
                new User(rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name")), userIds);
    }
}
