package playground.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import playground.domain.Category;
import playground.domain.Document;
import playground.domain.Team;
import playground.domain.User;
import playground.dto.DocumentOutboxResponse;
import playground.dto.DocumentRequest;
import playground.dto.DocumentResponse;
import playground.dto.TeamResponse;
import playground.repository.TeamRepositiory;
import playground.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class TeamService {
    @Autowired
    TeamRepositiory teamRepositiory;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public List<TeamResponse> findAll() {
        List<Team> teams = teamRepositiory.findAll();
        List<TeamResponse> teamResponses = new ArrayList<>();
        for (Team team : teams) {
            teamResponses.add(new TeamResponse(team));
        }
        return teamResponses;
    }
}
