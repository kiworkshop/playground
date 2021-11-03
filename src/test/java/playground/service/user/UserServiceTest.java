package playground.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import playground.controller.user.request.CreateUserRequest;
import playground.domain.user.User;
import playground.repository.user.UserRepository;

import java.util.Collections;
import java.util.List;

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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("사용자를 저장한다.")
    void save() {
        //given
        CreateUserRequest createUserRequest = new CreateUserRequest("a@naver.com", "Password123!", "김성빈");

        //when
        userService.save(createUserRequest);

        //then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("중복된 이메일일 경우, 예외가 발생한다.")
    void save_fail_duplicated_email() {
        //given
        CreateUserRequest createUserRequest = new CreateUserRequest("a@naver.com", "Password123!", "김성빈");
        given(userRepository.save(any(User.class))).willThrow(new DuplicateKeyException("이메일 중복"));

        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> userService.save(createUserRequest))
                .withMessageContaining("이미 가입된 이메일입니다.");
    }

    @Test
    @DisplayName("식별번호에 일치하는 모든 사용자를 조회한다.")
    void findAllById() {
        //given
        User mockUser = mock(User.class);
        given(userRepository.findAllById(anyList())).willReturn(Collections.singletonList(mockUser));

        //when
        List<User> users = userService.findAllById(Collections.singletonList(1L));

        //then
        assertThat(users).isNotEmpty();
    }

    @Test
    @DisplayName("식별번호에 일치하는 사용자를 조회한다.")
    void findById() {
        //given
        User mockUser = mock(User.class);
        given(userRepository.findById(anyLong())).willReturn(mockUser);

        //when
        User user = userService.findById(1L);

        //then
        assertThat(user).isNotNull();
    }

    @Test
    @DisplayName("식별번호에 일치하는 사용자가 존재하지 않을 경우, 예외가 발생한다.")
    void findById_fail_not_found_user() {
        //given
        given(userRepository.findById(anyLong())).willThrow(new IllegalArgumentException("해당하는 회원이 존재하지 않습니다"));

        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> userService.findById(1L))
                .withMessageContaining("해당하는 회원이 존재하지 않습니다");
    }
}
