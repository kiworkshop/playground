package playground.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import playground.domain.Document;
import playground.dto.DocumentRequest;
import playground.mapper.DocumentRowMapper;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@Component
public class DocumentDao {

    private final JdbcTemplate jdbcTemplate;
    private DocumentRowMapper documentRowMapper = new DocumentRowMapper();

    private static String findByIdQuery = "select * from document where id = ?";
    private static String findDocumentOutboxQuery = "select * from document where drafter_id = ? order by created_at desc";
    private static String insertDocumentQuery = "insert into document (title, category, contents, drafter_Id) values (?, ?, ?, ?) ";
    private static String insertDocumentApprovalQuery = "insert into document_approval (document_Id, approver_id, approval_order) values (?, ?, ?) ";

    public DocumentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Document findById(long id) {
        return jdbcTemplate.queryForObject(findByIdQuery, documentRowMapper, id);
    }

    public List<Document> findDocumentsOutbox(Long userId) {
        return jdbcTemplate.query(findDocumentOutboxQuery, documentRowMapper, userId);
    }

    public long insertDocument(DocumentRequest documentRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertDocumentQuery, new String[]{"id"});
            preparedStatement.setString(1, documentRequest.getTitle());
            preparedStatement.setString(2, documentRequest.getCategory());
            preparedStatement.setString(3, documentRequest.getContents());
            preparedStatement.setLong(4, documentRequest.getDrafterId());
            return preparedStatement;
        }, keyHolder);
        long documentId = keyHolder.getKey().longValue();
        insertDocumentApproval(documentId, documentRequest.getApproverIds());
        return documentId;
    }

    public void insertDocumentApproval(Long documentId, List<Long> approvalIds) {
        for (int i = 0; i < approvalIds.size(); i++) {
            jdbcTemplate.update(insertDocumentApprovalQuery, documentId, approvalIds.get(i), i);
        }
    }
}
