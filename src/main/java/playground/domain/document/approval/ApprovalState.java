package playground.domain.document.approval;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ApprovalState {

    DRAFTING("결재중"),
    APPROVED("승인"),
    CANCELED("거절");

    private final String text;

    public boolean isApproved() {
        return this == APPROVED;
    }

    public static ApprovalState findBy(String text) {
        return Arrays.stream(values())
                .filter(state -> state.text.equals(text))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("결재 상태 항목이 아닙니다."));
    }
}
