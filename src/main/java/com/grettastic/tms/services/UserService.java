package com.grettastic.tms.services;

import com.grettastic.tms.enums.Role;
import com.grettastic.tms.model.User;
import com.grettastic.tms.repo.UserRepository;
import com.grettastic.tms.responses.UserResponse;
import com.grettastic.tms.utils.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User is not found"));


        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

    public User registerUser(User user) {
        String encodedPassword = passwordEncoder().encode(user.getPassword());
        User savedUser = new User();
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword(encodedPassword);
        savedUser.setName(user.getName());
        savedUser.setRole(user.getRole() != null ? user.getRole() : Role.USER);
        return userRepo.save(savedUser);
    }

    public List<UserResponse> getUsers() {
        return UserMapper.toUserResponseList(userRepo.findAll());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("User is not found"));
    }

    public User getUser(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User is not found"));
    }
}
