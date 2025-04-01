package com.grettastic.tms.controllers;

import com.grettastic.tms.model.User;
import com.grettastic.tms.requests.LoginRequest;
import com.grettastic.tms.security.JwtUtil;
import com.grettastic.tms.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Validated
@Tag(name = "Authorisation", description = "API for register and login users")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Operation(
            summary = "Login the user",
            description = "This endpoint allows login the users and get token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully login and got the token"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                    loginRequest.getPassword()));
            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwt);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @Operation(
            summary = "register a user",
            description = "This endpoint allows an admin to register a users or admins.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully registered"),
                    @ApiResponse(responseCode = "400", description = "Invalid request format")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
    }
}
