package playground.service;

import learning.Category;
import learning.Document;
import learning.DocumentApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import playground.dto.DocumentDto;
import playground.repository.DocumentRepository;
import playground.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    public Optional<Document> findOne(Long documentId) {
        return documentRepository.findById(documentId);
    }

    public List<DocumentApproval> findOutBox(Long userId) {
        return documentRepository.findOutBox(userId);
    }

    public DocumentDto save(DocumentDto dto) {
        Document document = Document.create()
                .title(dto.getTitle())
                .category(Category.findBy(dto.getCategory()))
                .contents(dto.getContents())
                .drafter(userRepository.findById(dto.getDrafterId()).get())
                .build();

        Document save = documentRepository.save(document);
        return DocumentDto.convertFrom(save);
    }
}
