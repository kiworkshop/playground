package playground.repository;

import learning.*;
import org.springframework.beans.factory.annotation.Autowired;
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

import static playground.repository.SQLRepository.FIND_DOCUMENT_BY_ID;
import static playground.repository.SQLRepository.FINE_OUTBOX_DOCUMENTS;

@Repository
public class JdbcDocumentRepository implements DocumentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcDocumentRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Document findById(Long id) {
        return jdbcTemplate.queryForObject(FIND_DOCUMENT_BY_ID, documentRowMapper(), id);
    }

    @Override
    public List<Document> findOutBox(Long userId) {
        return jdbcTemplate.query(FINE_OUTBOX_DOCUMENTS, documentRowMapper(), userId);
    }

    @Override
    public Document save(Document document) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("document").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", document.getTitle());
        parameters.put("category", document.getCategory().getText());
        parameters.put("contents", document.getContents());
        parameters.put("drafter_id", document.getDrafter().getId());
        parameters.put("approvalState", document.getApprovalState().getText());

        jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return document;
    }

    public RowMapper<Document> documentRowMapper() {
        return (rs, rowNum) -> Document.rowMapper()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .contents(rs.getString("contents"))
                .category(Category.findBy(rs.getString("category")))
                .drafter(User.builder().id(rs.getLong("user_id")).name(rs.getString("user_name")).build())
                .approvalState(ApprovalState.findBy(rs.getString("approval_state")))
                .build();
    }
}
