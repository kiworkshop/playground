package playground.service.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.team.Team;
import playground.domain.team.TeamRepository;
import playground.service.team.dto.TeamResponse;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public List<TeamResponse> findAllTeams() {
        List<Team> teams = teamRepository.findAll();

        return teams.stream()
                .map(TeamResponse::new)
                .collect(Collectors.toList());
    }

}
