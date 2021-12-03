package playground.service.team;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import playground.domain.team.Team;
import playground.repository.team.TeamRepository;
import playground.service.team.request.CreateTeamRequest;
import playground.service.team.response.SelectTeamsResponse;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    @DisplayName("팀을 저장한다.")
    void save() {
        //given
        CreateTeamRequest createTeamRequest = new CreateTeamRequest("정산시스템팀");

        //when
        teamService.save(createTeamRequest);

        //then
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    @DisplayName("중복된 팀 명으로 저장할경우, 예외가 발생한다.")
    void save_fail_duplicated_name() {
        //given
        CreateTeamRequest createTeamRequest = new CreateTeamRequest("정산시스템팀");
        given(teamRepository.save(any(Team.class))).willThrow(new DuplicateKeyException("이름 중복"));

        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> teamService.save(createTeamRequest))
                .withMessageContaining("이미 존재하는 팀입니다.");
    }

    @Test
    @DisplayName("모든 팀을 조회한다.")
    void selectAll() {
        //given
        Team team1 = new Team("정산시스템팀");
        Team team2 = new Team("서비스개발팀");
        given(teamRepository.findAll()).willReturn(Arrays.asList(team1, team2));

        //when
        SelectTeamsResponse selectTeamsResponse = teamService.selectAll();

        //then
        assertThat(selectTeamsResponse).isNotNull();
    }

    @Test
    @DisplayName("존재하는 팀이 없을 경우, 예외가 발생한다.")
    void selectAll_return_empty() {
        //given
        given(teamRepository.findAll()).willReturn(Collections.emptyList());

        //when, then
        assertThatThrownBy(() -> teamService.selectAll())
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("존재하는 팀이 없습니다.");
    }

    @Test
    @DisplayName("식별번호에 일치하는 팀을 조회한다.")
    void findById() {
        //given
        Team mockTeam = mock(Team.class);
        given(teamRepository.findById(anyLong())).willReturn(Optional.of(mockTeam));

        //when
        Team team = teamService.findById(1L);

        //then
        assertThat(team).isNotNull();
    }

    @Test
    @DisplayName("식별번호에 일치하는 팀이 존재하지 않을 경우, 예외가 발생한다.")
    void findById_fail_not_found_team() {
        //given
        given(teamRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> teamService.findById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("해당하는 팀이 존재하지 않습니다");
    }
}
