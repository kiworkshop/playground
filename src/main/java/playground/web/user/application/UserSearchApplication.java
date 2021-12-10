package playground.web.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.user.Team;
import playground.domain.user.User;
import playground.service.user.UserService;
import playground.web.user.api.response.TeamResponse;
import playground.web.user.api.response.UserResponse;
import playground.web.user.api.request.UserRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserSearchApplication {

    private final UserService userService;

    public List<TeamResponse> findAllTeams() {
        List<Team> allTeams = userService.findAllTeams();
        return covertTeamResponseFrom(allTeams);
    }

    private List<TeamResponse> covertTeamResponseFrom(List<Team> teams) {
        return teams.stream()
            .map(TeamResponse::new)
            .collect(Collectors.toList());
    }

    public List<UserResponse> findUsersByTeam(UserRequest userRequest) {
        Optional<Team> team = userService.findTeamById(userRequest.getTeamId());

        if (team.isPresent()) {
            return convertUserResponseFrom(team.get().getUsers(), team.get());
        }
        return Collections.emptyList();
    }

    private List<UserResponse> convertUserResponseFrom(List<User> users, Team team) {
        return users.stream()
            .map(user -> new UserResponse(user, team))
            .collect(Collectors.toList());
    }
}
