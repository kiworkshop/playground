package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.DocumentApproval;
import playground.domain.document.DocumentApprovalRepository;

@RequiredArgsConstructor
@Service
public class DocumentApprovalService {

    private DocumentApprovalRepository documentApprovalRepository;

    @Transactional(readOnly = false)
    public Long create(DocumentApproval documentApproval) {
        return documentApprovalRepository.save(documentApproval);
    }
}
