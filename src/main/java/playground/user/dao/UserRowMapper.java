package playground.user.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import playground.user.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
