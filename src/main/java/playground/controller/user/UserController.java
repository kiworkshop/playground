package playground.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.service.user.UserService;
import playground.service.user.request.CreateUserRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final CreateUserRequest createUserRequest) {
        userService.save(createUserRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
