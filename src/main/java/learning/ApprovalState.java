package learning;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApprovalState {

    DRAFTING("결재중"),
    APPROVED("승인"),
    CANCELED("거절");

    private final String text;

    public ApprovalState approve() {
        if (this == APPROVED) {
            throw new IllegalArgumentException("이미 결재자로부터 승인된 문서입니다.");
        }

        if (this == CANCELED) {
            throw new IllegalArgumentException("이미 결재자로부터 거절된 문서입니다.");
        }

        return APPROVED;
    }

    public boolean isDrafting() {
        return this == DRAFTING;
    }

    public boolean isApproved() {
        return this == APPROVED;
    }
}
