package playground.domain.document.dto.param;

import lombok.Builder;
import lombok.Getter;
import playground.domain.document.dto.AddDocumentRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class AddDocumentApprovalParam {

    private final Long documentId;
    private final List<Long> approversId;

    public static AddDocumentApprovalParam of(Long documentId, AddDocumentRequest addDocumentRequest) {
        return AddDocumentApprovalParam.builder()
                .documentId(documentId)
                .approversId(Collections.unmodifiableList(new ArrayList<>(addDocumentRequest.getApproverIds())))
                .build();
    }
}
