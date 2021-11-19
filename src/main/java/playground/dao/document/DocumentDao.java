package playground.dao.document;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.domain.document.Document;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class DocumentDao {

    private final JdbcTemplate jdbcTemplate;

    public Document findById(Long id) {
        String sql = "select * from document where id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> Document.builder()
                        .id(id)
                        .title(rs.getString("title"))
                        .category(Category.valueOf(rs.getString("category")))
                        .contents(rs.getString("contents"))
                        .approvalState(ApprovalState.valueOf(rs.getString("approval_state")))
                        .drafterId(rs.getLong("drafter_id"))
                        .build(),
                id
        );
    }

    public List<Document> findStateDocumentsByDrafterId(Long drafterId, ApprovalState approvalState) {
        String sql = "select * from document where drafter_id = ? and approval_state = ?";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> Document.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .category(Category.valueOf(rs.getString("category")))
                        .approvalState(approvalState)
                        .drafterId(drafterId)
                        .build(),
                drafterId,
                approvalState.name()
        );
    }

    public Long save(Document document) {
        String sql = "insert into document(title, category, contents, approval_state, drafter_id) values(?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
                    pstmt.setString(1, document.getTitle());
                    pstmt.setString(2, document.getCategory().name());
                    pstmt.setString(3, document.getContents());
                    pstmt.setString(4, document.getApprovalState().name());
                    pstmt.setLong(5, document.getDrafterId());
                    return pstmt;
                },
                keyHolder
        );

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

}
