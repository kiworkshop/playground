package playground.repository;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import playground.domain.DocumentApproval;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class DocumentApprovalRepository {

    private final JdbcTemplate jdbcTemplate;

    public DocumentApprovalRepository(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveAll(final List<DocumentApproval> documentApprovals) {
        String saveQuery = "insert into document_approval (approver_id, document_id, approval_state, approval_order, approval_comment) " +
                "values(?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(saveQuery, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                DocumentApproval documentApproval = documentApprovals.get(i);
                ps.setLong(1, documentApproval.getApproverId());
                ps.setLong(2, documentApproval.getDocumentId());
                ps.setString(3, documentApproval.getApprovalState().name());
                ps.setInt(4, documentApproval.getApprovalOrder());
                ps.setString(5, documentApproval.getApprovalComment());
            }

            @Override
            public int getBatchSize() {
                return documentApprovals.size();
            }
        });
    }

    public List<DocumentApproval> findAll() {
        String selectAllQuery = "select * from document_approval";

        return jdbcTemplate.query(selectAllQuery, (rs, rowNum) ->
                DocumentApproval.builder()
                        .approverId(rs.getLong("approver_id"))
                        .approverId(rs.getLong("document_id"))
                        .approvalState(rs.getString("approval_state"))
                        .approvalOrder(rs.getInt("approval_order"))
                        .approvalComment(rs.getString("approval_comment"))
                        .build());
    }
}
