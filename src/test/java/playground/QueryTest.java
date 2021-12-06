package playground;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import playground.repository.document.DocumentApprovalRepository;
import playground.repository.document.DocumentRepository;
import playground.service.user.UserService;

@SpringBootTest
@Transactional
public class QueryTest {

    @Autowired
    private DocumentApprovalRepository documentApprovalRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("")
    void test() {
        //given

        //when

        //then
    }
}
