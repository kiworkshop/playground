package playground.controller.team;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.service.team.TeamService;
import playground.service.team.request.CreateTeamRequest;
import playground.service.team.response.SelectTeamsResponse;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(final TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final CreateTeamRequest createTeamRequest) {
        teamService.create(createTeamRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SelectTeamsResponse> findAll() {
        SelectTeamsResponse selectTeamsResponse = teamService.findAll();
        return ResponseEntity.ok(selectTeamsResponse);
    }
}
