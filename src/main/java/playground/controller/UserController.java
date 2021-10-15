package playground.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.controller.request.CreateUserRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
