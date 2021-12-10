package playground.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import playground.service.user.UserService;
import playground.service.user.request.CreateUserRequest;
import playground.service.user.response.SelectUserResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final CreateUserRequest createUserRequest) {
        userService.create(createUserRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<SelectUserResponse>> findAllUserInTeam(@RequestParam final Long teamId) {
        List<SelectUserResponse> selectUserResponses = userService.findAllUserInTeam(teamId);
        return ResponseEntity.ok(selectUserResponses);
    }
}
