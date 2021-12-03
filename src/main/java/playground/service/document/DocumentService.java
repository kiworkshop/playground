package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;
import playground.domain.document.DocumentRepository;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.exception.NotFoundException;
import playground.service.document.dto.DocumentRequest;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.OutboxResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentService {

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    public DocumentResponse findOne(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new NotFoundException("문서를 찾을 수 없습니다."));
        return DocumentResponse.of(document);
    }

    public List<OutboxResponse> findOutBox(Long userId) {
        List<Document> outBox = documentRepository.findOutBox(userId);
        return outBox.stream()
                .map(OutboxResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public DocumentResponse save(DocumentRequest request) {
        User drafter = findUser(request.getDrafterId());
        List<User> aprovers = createApproversInOrder(request);
        Document document = request.toDocument(drafter, aprovers);

        documentRepository.save(document);
        return DocumentResponse.of(document);
    }

    private List<User> createApproversInOrder(DocumentRequest request) {
        Map<Long, User> approvers = findApprovers(request);

        List<User> orderApprovers = new ArrayList<>();
        for (Long approverId : request.getApproverIds()) {
            orderApprovers.add(approvers.get(approverId));
        }

        return orderApprovers;
    }

    private Map<Long, User> findApprovers(DocumentRequest request) {
        List<User> approvers = userRepository.findAllById(request.getApproverIds());
        return approvers.stream()
                .collect(Collectors.toMap(User::getId, user -> user));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("문서 기안 사용자를 찾을 수 없습니다."));
    }

    private User findApprover(Long approvalId) {
        return userRepository.findById(approvalId)
                .orElseThrow(() -> new NotFoundException("문서 결재 사용자를 찾을 수 없습니다."));
    }
}
