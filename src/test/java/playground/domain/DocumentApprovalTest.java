package playground.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentApprovalTest {

    @Test
    @DisplayName("결재 정보를 저장한다.")
    void create() {
        //given
        long approverId = 1L;
        long documentId = 1L;
        int approvalOrder = 1;

        //when
        DocumentApproval documentApproval = DocumentApproval.of(approverId, documentId, approvalOrder);

        //then
        assertThat(documentApproval)
                .extracting("approverId", "documentId", "approvalState", "approvalOrder", "approvalComment")
                .containsExactly(approverId, documentId, ApprovalState.DRAFTING, approvalOrder, null);
    }
}
