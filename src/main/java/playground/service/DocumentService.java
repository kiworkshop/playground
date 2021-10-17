package playground.service;

import learning.Category;
import learning.Document;
import learning.DocumentApproval;
import learning.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import playground.dto.DocumentDto;
import playground.repository.DocumentRepository;
import playground.repository.UserRepository;

import java.util.List;
import java.util.Optional;
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

    public DocumentDto findOne(Long documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        if (document.isPresent()) {
            return DocumentDto.convertFrom(document.get());
        }
        return DocumentDto.builder().build();
    }

    public List<DocumentDto> findOutBox(Long userId) {
        List<Document> outBox = documentRepository.findOutBox(userId);
        return outBox.stream()
                .map(DocumentDto::convertOutBoxFrom)
                .collect(Collectors.toList());
    }

    public DocumentDto save(DocumentDto dto) {
        Document document = Document.create()
                .title(dto.getTitle())
                .category(Category.valueOf(dto.getCategory()))
                .contents(dto.getContents())
                .drafter(User.builder().id(dto.getUserId()).name(dto.getUserName()).build())
                .build();

        Document save = documentRepository.save(document);
        //TODO documentApproval save

        return DocumentDto.convertFrom(save);
    }
}
