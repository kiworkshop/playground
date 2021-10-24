package playground.domain.document.dto.sql;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import static playground.domain.document.entity.ApprovalState.DEFAULT_APPROVAL_STATE_TEXT;

public class AddDocumentApprovalSqlParam extends MapSqlParameterSource {

    public static SqlParameterSource of(Long documentId, Long approverId) {
        return new MapSqlParameterSource()
                .addValue("document_id", documentId)
                .addValue("approver_id", approverId)
                .addValue("approval_state", DEFAULT_APPROVAL_STATE_TEXT);
    }
}
