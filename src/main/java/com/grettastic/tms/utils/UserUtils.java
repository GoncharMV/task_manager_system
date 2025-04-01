package com.grettastic.tms.utils;

import com.grettastic.tms.enums.Role;
import com.grettastic.tms.model.User;
import com.grettastic.tms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserUtils {
    private final UserService userService;

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User userDetails) {
            return userDetails.getUsername();
        }

        throw new RuntimeException("User email not found in authentication");
    }

    public User findUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    public User findUserById(Long userId) {
        return userService.getUser(userId);
    }

    public boolean isAdmin(String principalName) {
        User user = findUserByEmail(principalName);
        return user != null && user.getRole() == Role.ADMIN;
    }
}
