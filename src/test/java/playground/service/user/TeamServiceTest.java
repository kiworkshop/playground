package playground.service.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import playground.domain.user.Team;
import playground.domain.user.TeamRepository;
import playground.service.user.dto.TeamResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @AfterEach
    public void cleanUp() {
        teamRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("전체 팀 조회")
    void findTeams() {
        // given
        teamRepository.save(new Team("정산시스템팀"));
        teamRepository.save(new Team("서비스개발팀"));

        // when
        List<TeamResponse> teams = teamService.findAll();

        // then
        assertThat(teams).hasSize(2);
        assertThat(teams).extracting("name")
                .containsExactly("정산시스템팀", "서비스개발팀");
    }
}
