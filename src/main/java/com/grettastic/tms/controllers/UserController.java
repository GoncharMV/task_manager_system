package com.grettastic.tms.controllers;

import com.grettastic.tms.responses.UserResponse;
import com.grettastic.tms.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "Users", description = "API for controlling users")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Retrieve all users",
            description = "This endpoint returns a list of all tasks. Accessible only by admins.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
                    @ApiResponse(responseCode = "403", description = "Forbidden. User is not authorized to access this resource.")
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
