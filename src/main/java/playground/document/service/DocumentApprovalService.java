package playground.document.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.document.entity.DocumentApproval;
import playground.document.entity.DocumentApprovalRepository;

@RequiredArgsConstructor
@Service
public class DocumentApprovalService {

    private DocumentApprovalRepository documentApprovalRepository;

    @Transactional(readOnly = false)
    public Long create(DocumentApproval documentApproval) {
        return documentApprovalRepository.save(documentApproval);
    }
}
