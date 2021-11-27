package playground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.service.user.UserService;
import playground.service.user.dto.UserRequest;
import playground.service.user.dto.UserResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> find(@PathVariable Long id) {
        try {
            UserResponse user = userService.findOne(id);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> save(@RequestBody UserRequest requestDto) {
        UserResponse user = userService.save(requestDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
