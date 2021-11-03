package playground.service.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import playground.controller.document.request.CreateDocumentRequest;
import playground.domain.document.Document;
import playground.domain.user.User;
import playground.repository.document.DocumentApprovalRepository;
import playground.repository.document.DocumentRepository;
import playground.service.user.UserService;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentApprovalRepository documentApprovalRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private DocumentService documentService;

    @Test
    @DisplayName("문서를 저장한다.")
    void save() {
        //given
        User user = mock(User.class);
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest("교육비 결재 요청", "EDUCATION",
                "교육비", 1L, Collections.singletonList(1L));
        given(userService.findAllById(anyList())).willReturn(Arrays.asList(user, user));

        //when
        documentService.save(createDocumentRequest);

        //then
        verify(userService, times(1)).findAllById(anyList());
        verify(documentRepository, times(1)).save(any(Document.class));
        verify(documentApprovalRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("기안자 또는 결재자의 정보가 존재하지 않을 경우, 예외가 발생한다.")
    void save_fail_not_exist_drafter_or_approver() {
        //given
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest("교육비 결재 요청", "EDUCATION",
                "교육비", 1L, Collections.singletonList(1L));
        String errorMessage = "전달받은 회원 식별자와 일치하는 회원을 모두 찾지 못했습니다.";

        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> documentService.save(createDocumentRequest))
                .withMessage(errorMessage);
    }
}
