package playground.service.team;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.team.Team;
import playground.repository.team.TeamRepository;
import playground.service.team.request.CreateTeamRequest;
import playground.service.team.response.SelectTeamResponse;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(final TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional
    public void create(final CreateTeamRequest createTeamRequest) {
        Team team = createTeamRequest.toTeam();
        save(team);
    }

    private void save(final Team team) {
        try {
            teamRepository.save(team);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(String.format("[%s] 이미 존재하는 팀입니다.", team.getName()));
        }
    }

    @Transactional(readOnly = true)
    public List<SelectTeamResponse> findAll() {
        List<Team> teams = teamRepository.findAll();
        checkEmpty(teams);

        return teams.stream()
                .map(SelectTeamResponse::new)
                .collect(Collectors.toList());
    }

    private void checkEmpty(final List<Team> teams) {
        if (teams.isEmpty()) {
            throw new EntityNotFoundException("존재하는 팀이 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public Team findById(final Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("[%d] 식별번호에 해당하는 팀이 존재하지 않습니다.", teamId)));
    }
}
