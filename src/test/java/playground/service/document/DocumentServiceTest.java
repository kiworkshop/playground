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
import playground.service.document.response.SelectDocumentResponse;
import playground.service.user.UserService;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
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
                "교육비", 1L, Arrays.asList(1L, 2L));
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

    @Test
    @DisplayName("문서를 조회한다.")
    void select() {
        //given
        Document document = Document.builderForDao()
                .id(1L)
                .title("교육비 결재")
                .category("EDUCATION")
                .contents("교육비")
                .drafterId(1L)
                .approvalState("DRAFTING")
                .build();
        User user = new User("a@naver.com", "Password123!", "김성빈");
        given(documentRepository.findById(anyLong())).willReturn(document);
        given(userService.findById(anyLong())).willReturn(user);

        //when
        SelectDocumentResponse selectDocumentResponse = documentService.select(1L);

        //then
        assertThat(selectDocumentResponse).isNotNull();
    }

    @Test
    @DisplayName("문서가 존재하지 않을 시, 예외가 발생한다.")
    void select_fail_not_found_document() {
        //given
        String errorMessage = "해당하는 문서가 존재하지 않습니다.";
        given(documentRepository.findById(anyLong())).willThrow(new IllegalArgumentException(errorMessage));

        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> documentService.select(1L))
                .withMessageContaining(errorMessage);
    }

    @Test
    @DisplayName("기안자가 존재하지 않을 시, 예외가 발생한다.")
    void select_fail_not_found_user() {
        //given
        Document document = mock(Document.class);
        given(documentRepository.findById(anyLong())).willReturn(document);
        String errorMessage = "해당하는 회원이 존재하지 않습니다.";
        given(userService.findById(anyLong())).willThrow(new IllegalArgumentException(errorMessage));

        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> documentService.select(1L))
                .withMessageContaining(errorMessage);
    }
}
