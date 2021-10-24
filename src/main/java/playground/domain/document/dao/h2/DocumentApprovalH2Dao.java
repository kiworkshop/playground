package playground.domain.document.dao.h2;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import playground.domain.document.dao.DocumentApprovalDao;
import playground.domain.document.dto.AddDocumentApprovalParam;
import playground.domain.document.dto.sql.AddDocumentApprovalSqlParam;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DocumentApprovalH2Dao implements DocumentApprovalDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addApprovals(AddDocumentApprovalParam addDocumentApprovalParam) throws DataIntegrityViolationException {
        Long documentId = addDocumentApprovalParam.getDocumentId();
        List<Long> approversId = addDocumentApprovalParam.getApproversId();

        SimpleJdbcInsertOperations insertOperations = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("DOCUMENT_APPROVAL");

        approversId.stream()
                .map(approverId -> AddDocumentApprovalSqlParam.of(documentId, approverId))
                .forEach(insertOperations::execute);
    }
}
