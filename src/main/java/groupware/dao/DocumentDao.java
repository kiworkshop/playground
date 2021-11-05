package groupware.dao;

import groupware.domain.Category;
import groupware.domain.Document;
import groupware.domain.User;
import groupware.dto.DocumentRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Component
public class DocumentDao {

    private final JdbcTemplate jdbcTemplate;

    public DocumentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Document findById(long id) {
        String query = "select * from document where id = ?";
        List<Document> documents = jdbcTemplate.query(query, new RowMapper<Document>() {
            @Override
            public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
                return Document.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .category(Category.valueOf(rs.getString("category")))
                        .content(rs.getString("contents"))
                        .drafter(User.builder()
                                .id(rs.getLong("drafter_id"))
                                .build())
                        .createdAt(rs.getDate("created_at"))
                        .build();
            }
        }, id);

        return documents.get(0);
    }

    public List<Document> findDocumentsOutbox(Long userId) {
        String query = "select * from document where drafter_id = ? order by created_at desc";
        return jdbcTemplate.query(query, new RowMapper<Document>() {
            @Override
            public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
                return Document.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .category(Category.valueOf(rs.getString("category")))
                        .content(rs.getString("contents"))
                        .drafter(User.builder()
                                .id(rs.getLong("drafter_id"))
                                .build())
                        .build();
            }
        }, userId);

    }

    public long insertDocument(DocumentRequest documentRequest) {
        String query = "insert into document (title, category, contents, drafter_Id) values (?, ?, ?, ?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"id"});
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
        String query = "insert into document_approval (document_Id, approver_id, approval_order) values (?, ?, ?) ";
        for (int i = 0; i < approvalIds.size(); i++) {
            jdbcTemplate.update(query, documentId, approvalIds.get(i), i);
        }
    }
}
