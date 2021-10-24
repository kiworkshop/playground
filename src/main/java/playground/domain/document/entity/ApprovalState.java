package playground.domain.document.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApprovalState {

    DRAFTING("결재중"),
    APPROVED("승인"),
    CANCELED("거절");

    private final String text;

    public static final String DEFAULT_APPROVAL_STATE_TEXT = DRAFTING.name();
}
