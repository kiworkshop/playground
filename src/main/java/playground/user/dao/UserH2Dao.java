package playground.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import playground.document.entity.Document;
import playground.user.entity.User;

@Repository
@RequiredArgsConstructor
public class UserH2Dao implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public User findDrafterOf(Document document) throws IncorrectResultSizeDataAccessException {
        Long id = document.getDrafter().getId();
        String query = "SELECT * FROM USER WHERE ID=" + id;
        return jdbcTemplate.queryForObject(query, userRowMapper);
    }
}
