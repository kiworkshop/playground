package playground.domain.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import playground.domain.document.entity.Document;
import playground.domain.user.entity.User;

@Repository
@RequiredArgsConstructor
public class UserH2Dao implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public User findDrafterOf(Document document) {
        Long id = document.getDrafter().getId();
        String query = "SELECT * FROM USER WHERE ID=" + id;
        return jdbcTemplate.queryForObject(query, userRowMapper);
    }
}
