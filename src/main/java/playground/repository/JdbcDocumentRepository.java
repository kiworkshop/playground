package playground.repository;

import learning.ApprovalState;
import learning.Category;
import learning.Document;
import learning.DocumentApproval;
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
    private final UserRepository userRepository;

    @Autowired
    public JdbcDocumentRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userRepository = new JdbcUserRepository(dataSource);
    }

    @Override
    public Optional<Document> findById(Long id) {
        List<Document> result = jdbcTemplate.query(FIND_DOCUMENT_BY_ID, documentRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<DocumentApproval> findOutBox(Long userId) {
        return jdbcTemplate.query(FINE_OUTBOX_DOCUMENTS, documentApprovalRowMapper(), userId);
    }

//    @Override
//    public Document save(Document document) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement pstmt = con.prepareStatement(INSERT_DOCUMENT, new String[]{"ID"});
//                pstmt.setString(1, document.getTitle());
//                pstmt.setString(2, document.getCategory().getText());
//                pstmt.setString(3, document.getContents());
//                pstmt.setLong(4, document.getDrafter().getId());
//                pstmt.setString(5, document.getApprovalState().getText());
//                return pstmt;
//            }
//        }, keyHolder);
//
//        Number key = keyHolder.getKey();
//        return findById(key.longValue()).get();
//    }

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
                .drafter(userRepository.findById(rs.getLong("drafter_id")).get())
                .approvalState(ApprovalState.findBy(rs.getString("approval_state")))
                .build();
    }

    private RowMapper<DocumentApproval> documentApprovalRowMapper() {
        return (rs, rowNum) -> DocumentApproval.rowMapper()
                .approver(userRepository.findById(rs.getLong("approver_id")).get())
                .approvalState(ApprovalState.findBy(rs.getString("approval_state")))
                .approvalOrder(rs.getInt("approval_order"))
                .approvalComment(rs.getString("approval_comment"))
                .build();
    }
}
