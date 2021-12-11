package playground.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.service.user.UserService;
import playground.service.user.dto.UserResponse;
import playground.web.user.dto.UsersByTeamIdRequest;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public List<UserResponse> findUsersByTeamId(UsersByTeamIdRequest requestDto) {
        return userService.findUsersByTeam(requestDto.getTeamId());
    }


}
