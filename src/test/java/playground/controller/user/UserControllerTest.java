package playground.controller.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import playground.common.AbstractControllerTest;
import playground.domain.team.Team;
import playground.domain.user.User;
import playground.service.user.UserService;
import playground.service.user.request.CreateUserRequest;
import playground.service.user.response.SelectUsersInTeamResponse;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static playground.domain.user.vo.JobPosition.TEAM_MEMBER;

public class UserControllerTest extends AbstractControllerTest {

    @Mock
    private UserService userService;

    @Override
    protected Object setController() {
        return new UserController(userService);
    }

    @Test
    @DisplayName("사용자 생성 요청을 받아 사용자를 생성 후, HTTP 201을 반환한다.")
    void create() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("seongbeen93@naver.com", "password", "김성빈", "정산시스템팀", TEAM_MEMBER.getText());

        mockMvc.perform(post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {"idnaver.com", "", "@naver.com"})
    @DisplayName("이메일 형식이 올바르지 않을 경우, 예외가 발생한다.")
    void create_fail_invalid_email(String invalidEmail) throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest(invalidEmail, "password", "김성빈", "정산시스템팀", TEAM_MEMBER.getText());

        mockMvc.perform(post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("비밀번호가 공백 또는 null있을 경우, 예외가 발생한다.")
    void create_fail_empty_password(String invalidPassword) throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("seongbeen93@naver.com", invalidPassword, "김성빈", "정산시스템팀", TEAM_MEMBER.getText());

        mockMvc.perform(post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름이 공백 또는 null일 경우, 예외가 발생한다.")
    void create_fail_empty_name(String invalidName) throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("seongbeen93@naver.com", "password", invalidName, "정산시스템팀", TEAM_MEMBER.getText());

        mockMvc.perform(post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("팀명이 공백 또는 null일 경우, 예외가 발생한다.")
    void create_fail_empty_team(String invalidTeamName) throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("seongbeen93@naver.com", "password", "김성빈", invalidTeamName, TEAM_MEMBER.getText());

        mockMvc.perform(post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("특정 팀에 속한 사용자 조회 요청을 전달받아, 사용자들의 정보를 반환한다.")
    void selectUsersInTeam() throws Exception {
        User user = mock(User.class);
        Team team = mock(Team.class);
        given(user.getId()).willReturn(1L);
        given(user.getName()).willReturn("김성빈");
        given(user.getJobPosition()).willReturn(TEAM_MEMBER);
        given(user.getTeam()).willReturn(team);
        given(team.getName()).willReturn("운영팀");
        SelectUsersInTeamResponse selectUsersInTeamResponse = new SelectUsersInTeamResponse(Collections.singletonList(user));
        given(userService.selectUsersInTeam(anyLong())).willReturn(selectUsersInTeamResponse);

        mockMvc.perform(get("/api/users")
                .param("teamId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
