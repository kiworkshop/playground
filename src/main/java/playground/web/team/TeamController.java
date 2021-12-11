package playground.web.team;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.service.team.TeamService;
import playground.service.team.dto.TeamResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/api/teams")
    public ResponseEntity<List<TeamResponse>> findAllTeams() {
        List<TeamResponse> teamResponses = teamService.findAllTeams();
        return ResponseEntity.ok(teamResponses);
    }

}
