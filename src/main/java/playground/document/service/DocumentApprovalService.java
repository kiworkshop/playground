package playground.document.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playground.document.entity.DocumentApproval;
import playground.document.entity.DocumentApprovalRepository;

@RequiredArgsConstructor
@Service
public class DocumentApprovalService {

    private DocumentApprovalRepository documentApprovalRepository;

    public Long create(DocumentApproval documentApproval) {
        return documentApprovalRepository.save(documentApproval);
    }
}
