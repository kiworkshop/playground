package playground.service.team;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import playground.domain.team.Team;
import playground.repository.team.TeamRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    @DisplayName("팀 이름을 팀을 조회한다.")
    void findByName() {
        //given
        String name = "정산서비스팀";
        Team team = new Team(name);
        given(teamRepository.findByName(anyString())).willReturn(Optional.of(team));

        //when
        Team fetchedTeam = teamService.findByName(name);

        //then
        assertThat(fetchedTeam).isEqualTo(team);
    }

    @Test
    @DisplayName("팀 이름과 일치하는 팀이 존재하지 않을 경우, 예외가 발생한다.")
    void findByName_fail_not_found() {
        //given
        given(teamRepository.findByName(anyString())).willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> teamService.findByName("존재하지 않는 팀 이름"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("팀이 존재하지 않습니다.");
    }
}
