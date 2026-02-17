package app.user.service;

import app.user.dto.CreateUserRequest;
import app.user.dto.UpdateUserRequest;
import app.user.dto.UserResponse;
import app.user.model.User;
import app.user.repo.UserRepository;
import app.user.dto.LoginRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // -------------------------
    // GET ALL USERS
    // -------------------------
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // -------------------------
    // GET USER BY ID
    // -------------------------
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return toResponse(user);
    }

    // -------------------------
    // CREATE USER
    // -------------------------
    public UserResponse createUser(CreateUserRequest req) {

        // Optional but recommended:
        // if (userRepository.existsByEmail(req.getEmail()))
        //     throw new RuntimeException("Email already exists");

        User u = new User();
        u.setFirstName(req.getFirstName());
        u.setLastName(req.getLastName());
        u.setEmail(req.getEmail());
        u.setPhone(req.getPhone());
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));

        User saved = userRepository.save(u);

        return toResponse(saved);
    }

    // -------------------------
    // UPDATE USER
    // -------------------------
    public UserResponse updateUser(Long id, UpdateUserRequest req) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getFirstName() != null)
            user.setFirstName(req.getFirstName());

        if (req.getLastName() != null)
            user.setLastName(req.getLastName());

        if (req.getEmail() != null)
            user.setEmail(req.getEmail());

        if (req.getPhone() != null)
            user.setPhone(req.getPhone());

        User saved = userRepository.save(user);

        return toResponse(saved);
    }

    // -------------------------
    // DELETE USER
    // -------------------------
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new RuntimeException("User not found");

        userRepository.deleteById(id);
    }

    // -------------------------
    // MAPPER (Entity -> Response)
    // -------------------------
    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone()
        );
    }
    public UserResponse validateLogin(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElse(null);

        // if user doesn't exist OR password doesn't match -> return "unknown"
        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            // match your frontend check: response.email === "unknown"
            return new UserResponse(null, null, null, "unknown", null);
        }

        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}

