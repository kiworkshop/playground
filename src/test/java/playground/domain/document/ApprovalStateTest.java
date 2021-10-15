package playground.domain.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ApprovalStateTest {

    @ParameterizedTest
    @CsvSource(value = {"DRAFTING, false", "CANCELED, false", "APPROVED, true"})
    @DisplayName("승인 상태인지 확인한다.")
    void isApproved(ApprovalState approvalState, boolean expected) {
        //when
        boolean isApproved = approvalState.isApproved();

        //then
        assertThat(isApproved).isEqualTo(expected);
    }
}
