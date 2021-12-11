package playground.service.document;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import playground.domain.document.DocumentRepository;
import playground.domain.user.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DocumentServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
        documentRepository.deleteAllInBatch();
    }

    @DisplayName("")
    @Test
    void name() {
        // given

        // when

        // then
    }
}
