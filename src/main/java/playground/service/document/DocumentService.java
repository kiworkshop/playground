package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;
import playground.domain.document.DocumentRepository;
import playground.domain.user.UserRepository;
import playground.service.document.dto.DocumentRequest;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.OutboxResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public DocumentResponse findOne(Long documentId) {
        Document document = documentRepository.findById(documentId);
        return DocumentResponse.convertFrom(document);
    }

    public List<OutboxResponse> findOutBox(Long userId) {
        List<Document> outBox = documentRepository.findOutBox(userId);
        return outBox.stream()
                .map(OutboxResponse::convertFrom)
                .collect(Collectors.toList());
    }

    @Transactional
    public DocumentResponse save(DocumentRequest dto) {
        Document document = Document.create()
                .title(dto.getTitle())
                .category(dto.getCategory())
                .contents(dto.getContents())
                .drafter(userRepository.findById(dto.getDrafterId()))
                .build();

        Long id = documentRepository.save(document);
        //TODO documentApproval save

        return DocumentResponse.convertFrom(document);
    }
}
