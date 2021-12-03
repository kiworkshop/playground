package playground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.service.user.UserService;
import playground.service.user.dto.TeamUserResponse;
import playground.service.user.dto.UserRequest;
import playground.service.user.dto.UserResponse;

import java.net.URI;
import java.util.List;

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

    @GetMapping(value = "/users")
    public ResponseEntity<List<TeamUserResponse>> findByTeam(@RequestParam("teamId") Long id) {
        try {
            List<TeamUserResponse> users = userService.findUserByTeam(id);
            return ResponseEntity.ok(users);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
