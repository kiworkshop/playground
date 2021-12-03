package playground.service.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.document.vo.ApprovalState;
import playground.domain.document.vo.Category;
import playground.domain.team.Team;
import playground.domain.user.User;
import playground.domain.user.vo.JobPosition;
import playground.repository.document.DocumentApprovalRepository;
import playground.repository.document.DocumentRepository;
import playground.service.document.request.CreateDocumentRequest;
import playground.service.document.response.SelectDocumentResponse;
import playground.service.document.response.SelectSingleOutBoxResponse;
import playground.service.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void create() {
        //given
        User user = mock(User.class);
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest("교육비 결재 요청", "EDUCATION",
                "교육비", 1L, Arrays.asList(1L, 2L));
        given(userService.findAllById(anyList())).willReturn(Arrays.asList(user, user));

        //when
        documentService.create(createDocumentRequest);

        //then
        verify(userService, times(1)).findAllById(anyList());
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    @DisplayName("결재자가 한 명이라도 존재하지 않을 경우, 예외가 발생한다.")
    void create_fail_not_exist_approver() {
        //given
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest("교육비 결재 요청", "EDUCATION",
                "교육비", 1L, Collections.singletonList(1L));
        String errorMessage = "식별번호와 일치하는 결재자를 모두 찾지 못했습니다.";

        //when, then
        assertThatThrownBy(() -> documentService.create(createDocumentRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(errorMessage);
    }

    @Test
    @DisplayName("문서를 조회한다.")
    void find() {
        //given
        User drafter = mock(User.class);
        given(drafter.getId()).willReturn(1L);
        given(drafter.getName()).willReturn("기안자");
        given(drafter.getJobPosition()).willReturn(JobPosition.PART_MANAGER);

        Team team = mock(Team.class);
        given(drafter.getTeam()).willReturn(team);

        Document document = mock(Document.class);
        given(document.getId()).willReturn(1L);
        given(document.getDrafter()).willReturn(drafter);
        given(document.getTitle()).willReturn("교육비 결재");
        given(document.getCategory()).willReturn(Category.EDUCATION);
        given(document.getContents()).willReturn("교육비");
        given(document.getApprovalState()).willReturn(ApprovalState.DRAFTING);

        DocumentApproval documentApproval = mock(DocumentApproval.class);
        given(documentApproval.getApprover()).willReturn(drafter);
        given(documentApproval.getApprovalState()).willReturn(ApprovalState.DRAFTING);
        given(documentApprovalRepository.findAllDocumentApprovalAndApproverAndTeamByIds(anyList())).willReturn(Collections.singletonList(documentApproval));
        given(documentRepository.findDocumentAndDrafterById(anyLong())).willReturn(Optional.of(document));

        //when
        SelectDocumentResponse selectDocumentResponse = documentService.find(1L);

        //then
        assertThat(selectDocumentResponse).isNotNull();
    }

    @Test
    @DisplayName("문서가 존재하지 않을 시, 예외가 발생한다.")
    void find_fail_not_found_document() {
        //when, then
        assertThatThrownBy(() -> documentService.find(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("해당하는 문서가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("Outbox 문서 리스트를 조회한다.")
    void findOutBox() {
        //given
        Document document = mock(Document.class);
        given(document.getId()).willReturn(1L);
        given(document.getTitle()).willReturn("교육비 결재");
        given(document.getCategory()).willReturn(Category.EDUCATION);
        given(document.getApprovalState()).willReturn(ApprovalState.DRAFTING);

        given(documentRepository.findAllDocumentAndDrafterByDrafterIdAndApprovalState(anyLong(), any(ApprovalState.class)))
                .willReturn(Collections.singletonList(document));

        //when
        List<SelectSingleOutBoxResponse> selectMultiOutboxResponse = documentService.findOutBox(1L);

        //then
        assertThat(selectMultiOutboxResponse).hasSize(1);
    }

    @Test
    @DisplayName("Outbox 문서가 없다면, 예외가 발생한다.")
    void findOutBox_fail_empty_result() {
        //given
        String errorMessage = "현재 결재중인 문서가 존재하지 않습니다.";
        given(documentRepository.findAllDocumentAndDrafterByDrafterIdAndApprovalState(anyLong(), any(ApprovalState.class)))
                .willReturn(Collections.emptyList());

        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> documentService.findOutBox(1L))
                .withMessageContaining(errorMessage);
    }
}
