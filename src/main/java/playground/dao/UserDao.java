package playground.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import playground.domain.User;
import playground.mapper.UserRowMapper;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private UserRowMapper userRowMapper = new UserRowMapper();
    private static String findByIdQuery = "select * from user where id = ?";

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(Long userId){
        return jdbcTemplate.queryForObject(findByIdQuery, userRowMapper, userId);
    }
}
