package playground.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import playground.common.AbstractControllerTest;
import playground.controller.request.CreateUserRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest {

    @Override
    protected Object setController() {
        return new UserController();
    }

    @Test
    @DisplayName("사용자 생성 요청을 받아 사용자를 생성 후, HTTP 201을 반환한다.")
    void create() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("seongbeen93@naver.com", "password", "김성빈");

        mockMvc.perform(post("/api/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {"idnaver.com", "", "@naver.com"})
    @DisplayName("이메일 형식이 올바르지 않을 경우, 예외가 발생한다.")
    void create_fail_invalid_email(String invalidEmail) throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest(invalidEmail, "password", "김성빈");

        mockMvc.perform(post("/api/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("비밀번호가 공백 또는 null있을 경우, 예외가 발생한다.")
    void create_fail_empty_password(String invalidPassword) throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("seongbeen93@naver.com", invalidPassword, "김성빈");

        mockMvc.perform(post("/api/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름이 공백 또는 null일 경우, 예외가 발생한다.")
    void create_fail_empty_name(String invalidName) throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("seongbeen93@naver.com", "password", invalidName);

        mockMvc.perform(post("/api/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }
}
