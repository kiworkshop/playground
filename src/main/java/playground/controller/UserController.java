package playground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.service.user.UserService;
import playground.service.user.dto.UserRequest;
import playground.service.user.dto.UserResponse;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity<UserResponse> save(@RequestBody UserRequest requestDto) {
        UserResponse user = userService.saveUser(requestDto);
        return ResponseEntity.created(URI.create("users/" + user.getId())).body(user);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserResponse> find(@PathVariable Long id) {
        try {
            UserResponse user = userService.findOne(id);
            return ResponseEntity.ok(user);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
