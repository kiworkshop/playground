package playground.domain.document.dao.h2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import playground.domain.document.dao.DocumentApprovalDao;
import playground.domain.document.dto.AddDocumentApprovalParam;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
class DocumentApprovalH2DaoTest {

    @Autowired
    private DocumentApprovalDao documentApprovalDao;

    @Test
    @DisplayName("새 결재건을 추가한다.")
    void add_document_approval_success() {
        // given
        AddDocumentApprovalParam addDocumentApprovalParam = AddDocumentApprovalParam.builder()
                .documentId(1L)
                .approversId(Arrays.asList(1L, 2L))
                .build();

        // when, then
        assertThatNoException()
                .isThrownBy(() -> documentApprovalDao.addApprovals(addDocumentApprovalParam));
    }

    @Test
    @DisplayName("존재하지 않는 유저를 결재자로 추가할 경우 예외가 발생한다.")
    void add_document_approval_fail_no_such_approver() {
        // given
        AddDocumentApprovalParam addDocumentApprovalParam = AddDocumentApprovalParam.builder()
                .documentId(1L)
                .approversId(Collections.singletonList(999999L))
                .build();

        // when, then
        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> documentApprovalDao.addApprovals(addDocumentApprovalParam));
    }
}