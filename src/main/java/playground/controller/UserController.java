package playground.controller;

import learning.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> find(@PathVariable Long id) {
        User user = userService.findOne(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
