package playground.domain.document.dto.param.sql;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import playground.domain.document.dto.param.AddDocumentParam;

import java.time.LocalDateTime;

public class AddDocumentSqlParam extends MapSqlParameterSource {

    public static SqlParameterSource of(AddDocumentParam addDocumentParam) {
        return new MapSqlParameterSource()
                .addValue("title", addDocumentParam.getTitle())
                .addValue("contents", addDocumentParam.getContents())
                .addValue("drafter_id", addDocumentParam.getDrafterId())
                .addValue("category", addDocumentParam.getCategoryText())
                .addValue("approval_state", addDocumentParam.getApprovalStateText())
                .addValue("created_at", LocalDateTime.now());
    }
}
