package playground.controller.team;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import playground.common.AbstractControllerTest;
import playground.service.team.TeamService;
import playground.service.team.request.CreateTeamRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest extends AbstractControllerTest {

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    @Override
    protected Object setController() {
        return teamController;
    }

    @Test
    @DisplayName("팀 생성 요청을 받아 팀을 생성 후, HTTP 201을 반환한다.")
    void create() throws Exception {
        CreateTeamRequest createTeamRequest = new CreateTeamRequest("정산시스템팀");

        mockMvc.perform(post("/api/teams")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTeamRequest)))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("팀 이름이 공백 또는 null있을 경우, 예외가 발생한다.")
    void create_fail_empty_name(String invalidName) throws Exception {
        CreateTeamRequest createTeamRequest = new CreateTeamRequest(invalidName);

        mockMvc.perform(post("/api/teams")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTeamRequest)))
                .andExpect(status().isBadRequest());
    }
}
