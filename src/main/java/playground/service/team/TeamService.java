package playground.service.team;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.team.Team;
import playground.repository.team.TeamRepository;
import playground.service.team.request.CreateTeamRequest;
import playground.service.team.response.SelectTeamsResponse;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(final TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional(readOnly = true)
    public Team findByName(final String teamName) {
        return teamRepository.findByName(teamName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("[%s] 팀 이름에 해당하는 팀이 존재하지 않습니다.", teamName)));
    }

    @Transactional
    public void save(final CreateTeamRequest createTeamRequest) {
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
    public SelectTeamsResponse selectAll() {
        List<Team> teams = teamRepository.findAll();
        checkEmpty(teams);
        return new SelectTeamsResponse(teams);
    }

    private void checkEmpty(final List<Team> teams) {
        if (teams.isEmpty()) {
            throw new EntityNotFoundException("존재하는 팀이 없습니다.");
        }
    }
}
