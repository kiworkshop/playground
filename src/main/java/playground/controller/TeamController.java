package playground.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.dto.TeamResponse;
import playground.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping()
    public List<TeamResponse> getTeams() {
        return teamService.findAll();
    }
}
