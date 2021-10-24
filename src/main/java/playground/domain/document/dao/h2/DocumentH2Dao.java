package playground.domain.document.dao.h2;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import playground.domain.document.dao.DocumentDao;
import playground.domain.document.dao.DocumentRowMapper;
import playground.domain.document.dto.param.AddDocumentParam;
import playground.domain.document.dto.param.sql.AddDocumentSqlParam;
import playground.domain.document.entity.Document;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DocumentH2Dao implements DocumentDao {

    private final JdbcTemplate jdbcTemplate;
    private final DocumentRowMapper documentRowMapper;

    @Override
    public Document findById(Long id) throws EmptyResultDataAccessException {
        String query = "SELECT * FROM DOCUMENT WHERE ID=" + id;

        return jdbcTemplate.queryForObject(query, documentRowMapper);
    }

    @Override
    public Long addDocument(AddDocumentParam addDocumentParam) {
        SimpleJdbcInsertOperations insertOperations = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("DOCUMENT")
                .usingGeneratedKeyColumns("ID");

        return insertOperations
                .executeAndReturnKey(AddDocumentSqlParam.of(addDocumentParam))
                .longValue();
    }

    @Override
    public List<Document> findByDrafter(Long drafterId) {
        String query = "SELECT * FROM DOCUMENT WHERE DRAFTER_ID=" + drafterId;

        return jdbcTemplate.query(query, documentRowMapper);
    }
}
