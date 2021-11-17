package playground.domain.document;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import playground.domain.user.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcDocumentRepository implements DocumentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Override
    public Document findById(Long id) {
        String query = "select * from document where id = ?";
        return jdbcTemplate.queryForObject(
                query,
                (rs, rowNum) -> Document.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .category(Category.findBy(rs.getString("category")))
                        .contents(rs.getString("contents"))
                        .drafter(userRepository.findById(rs.getLong("drafter_id")))
                        .build(),
                id);
    }

    @Override
    public List<Document> findOutBox(Long userId) {
        String query = "select * from document " +
                "inner join document_approval " +
                "on document.id = document_approval.document_id " +
                "where document_approval.approver_id = ? order by insert_date desc";

        return jdbcTemplate.query(
                query,
                (rs, rowNum) -> Document.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .category(Category.findBy(rs.getString("category")))
                        .build(),
                userId);
    }

    @Override
    public Long save(Document document) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into document(title, category, contents, drafter_id, approval_state) values (?, ?, ?, ?, ?)";

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(query, new String[]{"ID"});
                pstmt.setString(1, document.getTitle());
                pstmt.setString(2, document.getCategory().getText());
                pstmt.setString(3, document.getContents());
                pstmt.setLong(4, document.getDrafter().getId());
                pstmt.setString(5, document.getApprovalState().getText());
                return pstmt;
            }
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key.longValue();
    }
}
