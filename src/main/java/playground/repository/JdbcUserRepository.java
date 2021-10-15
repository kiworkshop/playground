package playground.repository;

import learning.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static playground.repository.SQLRepository.FIND_USER_BY_ID;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> result = jdbcTemplate.query(FIND_USER_BY_ID, userRowMapper(), id);
        return result.stream().findAny();
    }

//    @Override
//    public User save(User user) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement pstmt = con.prepareStatement(INSERT_USER, new String[] { "ID" });
//                pstmt.setString(1, user.getName());
//                return pstmt;
//            }
//        }, keyHolder);
//
//        Number key = keyHolder.getKey();
//        return findById(key.longValue()).get();
//    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());

        jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return user;
    }

    public RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> User.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
