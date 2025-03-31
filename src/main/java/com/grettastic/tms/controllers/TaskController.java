package com.grettastic.tms.controllers;

import com.grettastic.tms.model.Task;
import com.grettastic.tms.model.User;
import com.grettastic.tms.requests.TaskRequest;
import com.grettastic.tms.responses.TaskResponse;
import com.grettastic.tms.services.TaskService;
import com.grettastic.tms.utils.UserUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserUtils userUtils;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskResponse> createNewTask(@RequestBody TaskRequest task) {
        User author = userUtils.findUserByEmail(userUtils.getCurrentUserEmail());


        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createNewTask(task, author));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }


}
