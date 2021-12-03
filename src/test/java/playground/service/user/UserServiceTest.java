package playground.service.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import playground.domain.user.*;
import playground.service.user.dto.TeamUserResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static playground.domain.user.JobPosition.TEAM_LEADER;
import static playground.domain.user.JobPosition.TEAM_MEMBER;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @AfterEach
    public void cleanUp() {
        teamRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("팀에 속한 회원 조회")
    void findUSerByTeam() {
        // given
        Team team = teamRepository.save(new Team("정산시스템팀"));

        User user1 = createUser("user1", "pa@sw**d", "user1@gmail.com", team, TEAM_LEADER);
        User user2 = createUser("user2", "pa@sw**d", "user2@gmail.com", team, TEAM_MEMBER);
        userRepository.save(user1);
        userRepository.save(user2);

        // when
        List<TeamUserResponse> users = userService.findUserByTeam(team.getId());

        // then
        assertThat(users).extracting("name", "teamName", "jobPositionText")
                .containsExactly(tuple("user1", "정산시스템팀", "팀장"),
                        tuple("user2", "정산시스템팀", "팀원"));
    }

    private User createUser(String name, String password, String email, Team team, JobPosition jobPosition) {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .team(team)
                .jobPosition(jobPosition)
                .build();
    }
}
