package playground.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import playground.domain.team.Team;
import playground.domain.user.User;
import playground.repository.user.UserRepository;
import playground.service.team.TeamService;
import playground.service.user.request.CreateUserRequest;

import javax.persistence.EntityNotFoundException;
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
import static playground.domain.user.vo.JobPosition.TEAM_MEMBER;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("사용자를 저장한다.")
    void create() {
        //given
        Team team = mock(Team.class);
        given(teamService.findById(anyLong())).willReturn(team);
        CreateUserRequest createUserRequest = new CreateUserRequest("a@naver.com", "Password123!", "김성빈", 1L, TEAM_MEMBER.name());

        //when
        userService.create(createUserRequest);

        //then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("중복된 이메일일 경우, 예외가 발생한다.")
    void create_fail_duplicated_email() {
        //given
        Team team = mock(Team.class);
        given(teamService.findById(anyLong())).willReturn(team);
        given(userRepository.save(any(User.class))).willThrow(new DuplicateKeyException("이메일 중복"));
        CreateUserRequest createUserRequest = new CreateUserRequest("a@naver.com", "Password123!", "김성빈", 1L, TEAM_MEMBER.name());

        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> userService.create(createUserRequest))
                .withMessageContaining("이미 가입된 이메일입니다.");
    }

    @Test
    @DisplayName("팀이 존재하지 않을 경우, 에외가 발생한다.")
    void create_fail_team_not_found() {
        //given
        Team team = mock(Team.class);
        given(teamService.findById(anyLong())).willReturn(team);
        given(userRepository.save(any(User.class))).willThrow(new DuplicateKeyException("이메일 중복"));
        CreateUserRequest createUserRequest = new CreateUserRequest("a@naver.com", "Password123!", "김성빈", 1L, TEAM_MEMBER.name());

        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> userService.create(createUserRequest))
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
        given(userRepository.findById(anyLong())).willReturn(Optional.of(mockUser));

        //when
        User user = userService.findById(1L);

        //then
        assertThat(user).isNotNull();
    }

    @Test
    @DisplayName("식별번호에 일치하는 사용자가 존재하지 않을 경우, 예외가 발생한다.")
    void findById_fail_not_found_user() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> userService.findById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("해당하는 회원이 존재하지 않습니다");
    }
}
