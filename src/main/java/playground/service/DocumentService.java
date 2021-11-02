package playground.service;

import learning.Document;
import learning.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import playground.dto.DocumentRequest;
import playground.dto.DocumentResponse;
import playground.dto.OutboxResponse;
import playground.repository.DocumentRepository;
import playground.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

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

    public DocumentResponse save(DocumentRequest dto) {
        Document document = Document.create()
                .title(dto.getTitle())
                .category(dto.getCategory())
                .contents(dto.getContents())
                .drafter(userRepository.findById(dto.getDrafterId()))
                .build();

        Long id = documentRepository.save(document);
        //TODO documentApproval save

        Document saved = documentRepository.findById(id);
        return DocumentResponse.convertFrom(saved);
    }
}
