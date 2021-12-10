package playground.web.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.web.user.application.UserSearchApplication;
import playground.web.user.api.response.TeamResponse;
import playground.web.user.api.response.UserResponse;
import playground.web.user.api.request.UserRequest;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserSearchApplication userSearchApplication;

    @GetMapping(path = "/api/teams")
    public ResponseEntity<List<TeamResponse>> findAllTeams() {
        List<TeamResponse> teamResponseDtos = userSearchApplication.findAllTeams();
        return ResponseEntity.ok(teamResponseDtos);
    }

    @GetMapping(path = "/api/users")
    public ResponseEntity<List<UserResponse>> findUsersByTeam(UserRequest userRequest) {
        List<UserResponse> userResponseDtos = userSearchApplication.findUsersByTeam(userRequest);
        return ResponseEntity.ok(userResponseDtos);
    }

}
