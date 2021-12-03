package playground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.service.user.TeamService;
import playground.service.user.dto.TeamRequest;
import playground.service.user.dto.TeamResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping(value = "/team")
    public ResponseEntity<TeamResponse> save(@RequestBody TeamRequest request) {
        TeamResponse team = teamService.saveTeam(request);
        return ResponseEntity.created(URI.create("/teams/" + team.getId())).body(team);
    }

    @GetMapping(value = "teams")
    public ResponseEntity<List<TeamResponse>> findUserByTeam() {
        try {
            List<TeamResponse> teams = teamService.findTeams();
            return ResponseEntity.ok(teams);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
