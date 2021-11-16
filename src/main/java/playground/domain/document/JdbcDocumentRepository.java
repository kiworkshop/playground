package playground.domain.document;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import playground.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcDocumentRepository implements DocumentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Override
    public Document findById(Long id) {
        String query = "select * from document where id = ?";
        return jdbcTemplate.queryForObject(query, documentRowMapper(), id);
    }

    @Override
    public List<Document> findOutBox(Long userId) {
        String query = "select * from document " +
                "inner join document_approval " +
                "on document.id = document_approval.document_id " +
                "where document_approval.approver_id = ? order by insert_date desc";

        return jdbcTemplate.query(query, documentRowMapper(), userId);
    }

    @Override
    public Long save(Document document) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("document")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", document.getTitle());
        parameters.put("category", document.getCategory().getText());
        parameters.put("contents", document.getContents());
        parameters.put("drafter_id", document.getDrafter().getId());
        parameters.put("drafter_name", document.getDrafter().getName());
        parameters.put("approval_state", document.getApprovalState().getText());
        parameters.put("insert_date", LocalDateTime.now());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return key.longValue();
    }

    public RowMapper<Document> documentRowMapper() {
        return (rs, rowNum) -> Document.rowMapper()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .contents(rs.getString("contents"))
                .category(Category.findBy(rs.getString("category")))
                .drafter(userRepository.findById(rs.getLong("drafter_id")))
                .approvalState(ApprovalState.findBy(rs.getString("approval_state")))
                .insertDate(rs.getObject("insert_date", LocalDateTime.class))
                .updateDate(rs.getObject("update_date", LocalDateTime.class))
                .build();
    }
}
