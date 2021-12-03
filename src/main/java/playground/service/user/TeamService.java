package playground.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.user.Team;
import playground.domain.user.TeamRepository;
import playground.service.user.dto.TeamRequest;
import playground.service.user.dto.TeamResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    public List<TeamResponse> findTeams() {
        List<Team> teams = teamRepository.findAll();
        return TeamResponse.ofList(teams);
    }

    @Transactional
    public TeamResponse saveTeam(TeamRequest request) {
        Team team = teamRepository.save(request.toTeam());
        return TeamResponse.of(team);
    }

    public List<TeamResponse> findAll() {
        List<Team> team = teamRepository.findAll();
        return TeamResponse.ofList(team);
    }
}
