package playground.dao.document;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import playground.domain.document.DocumentApproval;

import java.sql.PreparedStatement;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class DocumentApprovalDao {

    private final JdbcTemplate jdbcTemplate;

    public Long save(DocumentApproval documentApproval) {
        String sql = "insert into document_approval(document_id, approver_id, approval_state, approval_order, approval_comment) values(?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
                    pstmt.setLong(1, documentApproval.getDocumentId());
                    pstmt.setLong(2, documentApproval.getApproverId());
                    pstmt.setString(3, documentApproval.getApprovalState().name());
                    pstmt.setInt(4, documentApproval.getApprovalOrder());
                    pstmt.setString(5, documentApproval.getApprovalComment());
                    return pstmt;
                },
                keyHolder
        );

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

}
