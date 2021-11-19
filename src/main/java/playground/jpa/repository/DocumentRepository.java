package playground.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.service.document.dto.DocumentResponseDto;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<DocumentResponseDto, Long> {

    public Optional<DocumentResponseDto> findById(Long id);
}
