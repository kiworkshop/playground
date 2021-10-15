package playground.document;

import playground.document.dto.DocumentDto;

public interface DocumentService {

    DocumentDto findById(Long id);
}
