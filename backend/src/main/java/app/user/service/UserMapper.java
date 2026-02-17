package app.user.service;

import app.user.dto.CreateUserRequest;
import app.user.dto.UpdateUserRequest;
import app.user.dto.UserResponse;
import app.user.model.User;

public class UserMapper {

    public static User toEntity(CreateUserRequest req) {
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPasswordHash(req.getPassword());
        // NOTE: this will be encoded in the service
        return user;
    }

    public static void applyUpdates(User user, UpdateUserRequest req) {
        if (req.getFirstName() != null) user.setFirstName(req.getFirstName());
        if (req.getLastName() != null) user.setLastName(req.getLastName());
        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getPhone() != null) user.setPhone(req.getPhone());
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone()
        );
    }

    }

