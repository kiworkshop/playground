package playground.dao.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import playground.domain.user.User;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public User findById(Long id) {
        String sql = "select * from user where id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> User.builder()
                        .id(id)
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .build(),
                id
        );
    }

    public List<User> findAllByIds(List<Long> ids) {
        String sql = "select * from user where id in (%s)";
        String inParams = ids.stream()
                .map(id -> "?")
                .collect(Collectors.joining(","));

        return jdbcTemplate.query(
                String.format(sql, inParams),
                (rs, rowNum) -> User.builder()
                        .id(rs.getLong("id"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .build(),
                ids.toArray()
        );
    }

}
