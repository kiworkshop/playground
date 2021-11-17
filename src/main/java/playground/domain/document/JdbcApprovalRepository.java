package playground.domain.document;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class JdbcApprovalRepository implements ApprovalRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAll(DocumentApprovals documentApprovals) {
        for (DocumentApproval documentApproval : documentApprovals.values()) {
            save(documentApproval);
        }
    }

    @Override
    public void save(DocumentApproval documentApproval) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into document_approval(document_id, approver_id, approval_state, approval_order) values (?, ?, ?, ?)";

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(query, new String[]{"ID"});
                pstmt.setLong(1, documentApproval.getDocumentId());
                pstmt.setLong(2, documentApproval.getApproverId());
                pstmt.setString(3, documentApproval.getApprovalState().getText());
                pstmt.setInt(4, documentApproval.getApprovalOrder());
                return pstmt;
            }
        }, keyHolder);
    }
}
