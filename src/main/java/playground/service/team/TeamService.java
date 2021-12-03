package playground.service.team;

import org.springframework.stereotype.Service;
import playground.domain.team.Team;
import playground.repository.team.TeamRepository;
import playground.service.team.request.CreateTeamRequest;

import javax.persistence.EntityNotFoundException;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(final TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team findByName(final String teamName) {
        return teamRepository.findByName(teamName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("[%s] 팀 이름에 해당하는 팀이 존재하지 않습니다.", teamName)));
    }

    public void save(final CreateTeamRequest createTeamRequest) {
        Team team = createTeamRequest.toTeam();
        teamRepository.save(team);
    }
}
