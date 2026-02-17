package app.user.controller;

import java.util.List;

import app.user.dto.CreateUserRequest;
import app.user.dto.UpdateUserRequest;
import app.user.dto.UserResponse;
import app.user.service.UserService;
import app.user.dto.LoginRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest req) {
        return userService.createUser(req);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest req) {
        return userService.updateUser(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/getUser")
    public UserResponse getUser(@RequestBody LoginRequest req) {
        return userService.validateLogin(req);
    }
}
