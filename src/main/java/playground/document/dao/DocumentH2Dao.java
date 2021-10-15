package playground.document.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import playground.document.entity.Document;

@Repository
@RequiredArgsConstructor
public class DocumentH2Dao implements DocumentDao {

    private final JdbcTemplate jdbcTemplate;
    private final DocumentRowMapper documentRowMapper;

    @Override
    public Document findById(Long id) throws IncorrectResultSizeDataAccessException {
        String query = "SELECT * FROM DOCUMENT WHERE ID=" + id;
        return jdbcTemplate.queryForObject(query, documentRowMapper);
    }
}
