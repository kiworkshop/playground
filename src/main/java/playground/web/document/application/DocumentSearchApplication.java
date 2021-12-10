package playground.web.document.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.user.Team;
import playground.domain.user.User;
import playground.service.document.DocumentService;
import playground.web.document.api.request.OutboxDocumentRequest;
import playground.web.document.api.response.DocumentApprovalResponse;
import playground.web.document.api.response.DocumentResponse;
import playground.web.document.api.response.OutboxDocumentResponse;
import playground.web.user.api.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

import static playground.domain.document.ApprovalState.DRAFTING;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DocumentSearchApplication {

    private final DocumentService documentService;

}
