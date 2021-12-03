package playground.controller.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import playground.common.AbstractControllerTest;
import playground.service.document.DocumentService;
import playground.service.document.request.CreateDocumentRequest;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DocumentControllerTest extends AbstractControllerTest {

    @Mock
    private DocumentService documentService;

    @Override
    protected Object setController() {
        return new DocumentController(documentService);
    }

    @Test
    @DisplayName("문서를 생성한다.")
    void create() throws Exception {
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest("교육비 결재 요청", "EDUCATION",
                "교육비", 1L, Collections.singletonList(1L));

        mockMvc.perform(post("/api/documents")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDocumentRequest)))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("문서 제목이 null 또는 빈 값일 경우 예외가 발생한다.")
    void create_fail_invalid_title(String invalidTitle) throws Exception {
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest(invalidTitle, "EDUCATION",
                "교육비", 1L, Collections.singletonList(1L));

        mockMvc.perform(post("/api/documents")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDocumentRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("문서 유형이 null 또는 빈 값일 경우 예외가 발생한다.")
    void create_fail_invalid_category(String invalidCategory) throws Exception {
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest("교육비 결재 요청", invalidCategory,
                "교육비", 1L, Collections.singletonList(1L));

        mockMvc.perform(post("/api/documents")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDocumentRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("문서 내용이 null 또는 빈 값일 경우 예외가 발생한다.")
    void create_fail_invalid_contents(String invalidContents) throws Exception {
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest("교육비 결재 요청", "EDUCATION",
                invalidContents, 1L, Collections.singletonList(1L));

        mockMvc.perform(post("/api/documents")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDocumentRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("문서 기안자의 식별 번호가 1미만일 경우 예외가 발생한다.")
    void create_fail_invalid_drafter_id() throws Exception {
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest("교육비 결재 요청", "EDUCATION",
                "교육비", 0L, Collections.singletonList(1L));

        mockMvc.perform(post("/api/documents")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDocumentRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("문서 승인자 목록이 null 또는 비어있을 경우 예외가 발생한다.")
    void create_fail_empty_approver_ids(List<Long> invalidApproverIds) throws Exception {
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest("교육비 결재 요청", "EDUCATION",
                "교육비", 1L, invalidApproverIds);

        mockMvc.perform(post("/api/documents")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDocumentRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("문서를 조회한다.")
    void find() throws Exception {
        mockMvc.perform(get("/api/documents/{documentId}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Outbox 문서 리스트를 조회한다.")
    void findOutBox() throws Exception {
        mockMvc.perform(get("/api/documents/outbox")
                .param("drafterId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("모든 문서 항목을 조회한다.")
    void findCategories() throws Exception {
        mockMvc.perform(get("/api/documents/categories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
