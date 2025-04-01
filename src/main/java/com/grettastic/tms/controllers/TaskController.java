package com.grettastic.tms.controllers;

import com.grettastic.tms.model.User;
import com.grettastic.tms.requests.CommentRequest;
import com.grettastic.tms.requests.TaskRequest;
import com.grettastic.tms.responses.CommentResponse;
import com.grettastic.tms.responses.TaskResponse;
import com.grettastic.tms.services.CommentService;
import com.grettastic.tms.services.TaskService;
import com.grettastic.tms.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Tasks", description = "API for controlling the tasks")
public class TaskController {
    private final TaskService taskService;
    private final CommentService commentService;
    private final UserUtils userUtils;

    @Operation(
            summary = "Create a new task",
            description = "This endpoint allows an admin to create a new task.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Task successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid request format"),
                    @ApiResponse(responseCode = "403", description = "Forbidden. User is not authorized to create a task.")
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskResponse> createNewTask(@RequestBody @Valid TaskRequest task) {
        User author = userUtils.findUserByEmail(userUtils.getCurrentUserEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createNewTask(task, author));
    }

    @Operation(
            summary = "Retrieve all tasks",
            description = "This endpoint returns a list of all tasks. Accessible only by admins.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of tasks retrieved successfully"),
                    @ApiResponse(responseCode = "403", description = "Forbidden. User is not authorized to access this resource.")
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @Operation(
            summary = "Update a Task",
            description = "This endpoint updates the details of a task. Only accessible by users with the 'ADMIN' role or those who are assigned as task executors.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task successfully updated"),
                    @ApiResponse(responseCode = "403", description = "Forbidden. User is not authorized to update the task."),
                    @ApiResponse(responseCode = "404", description = "Task not found.")
            }
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @taskService.isTaskExecutor(#id, authentication.name)")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                   @RequestBody @Valid TaskRequest task,
                                                   Principal principal) throws AccessDeniedException {
        return ResponseEntity.ok(taskService.updateTask(id, task, principal));
    }

    @Operation(summary = "Delete a task", description = "Deletes a task by ID (accessible only to administrators).",
            responses = {
            @ApiResponse(responseCode = "204", description = "Task successfully deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden: User is not authorized to delete the tasks."),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTask(@Parameter(description = "Task ID") @PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get task by ID", description = "Retrieves a task by its ID. Accessible to administrators and task executors.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Task retrieved successfully"),
                @ApiResponse(responseCode = "403", description = "Forbidden: User is not authorized to view this task."),
                @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @taskService.isTaskExecutor(#id, authentication.name)")
    public ResponseEntity<TaskResponse> getTaskById(@Parameter(description = "Task ID") @PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(summary = "Get created tasks", description = "Retrieves all tasks created by this admin.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
                    @ApiResponse(responseCode = "403", description = "Forbidden: User is not authorized to view this tasks")
            })
    @GetMapping("/{userId}/created")
    public ResponseEntity<List<TaskResponse>> getCreatedTasks(@Parameter(description = "User ID") @PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getCreatedTasks(userId));
    }

    @Operation(summary = "Get assigned tasks", description = "Retrieves all tasks assigned to the user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
                    @ApiResponse(responseCode = "403", description = "Forbidden: User is not authorized to view this tasks")
            })
    @GetMapping("/{userId}/assigned")
    public ResponseEntity<List<TaskResponse>> getAssignedTasks(@Parameter(description = "User ID") @PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getAssignedTasks(userId));
    }

    @Operation(
            summary = "Create a new comment to the task",
            description = "This endpoint allows an admin or assigned user to create a new comments on the tasks.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Comment successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid request format"),
                    @ApiResponse(responseCode = "403", description = "Forbidden. User is not authorized to create a comment.")
            }
    )
    @PostMapping("/{id}/comments")
    @PreAuthorize("hasRole('ADMIN') or @taskService.isTaskExecutor(#id, authentication.name)")
    public ResponseEntity<CommentResponse> postComment(@Parameter(description = "Task ID") @PathVariable Long id,
                                                       @RequestBody @Valid CommentRequest comment) {
        User author = userUtils.findUserByEmail(userUtils.getCurrentUserEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.postComment(id, comment, author));
    }

    @Operation(summary = "Get comments to the task", description = "Retrieves all comments for a specific task. Available to administration and assigned user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
                    @ApiResponse(responseCode = "403", description = "Forbidden: User is not authorized to view this comments")
            })
    @GetMapping("/{id}/comments")
    @PreAuthorize("hasRole('ADMIN') or @taskService.isTaskExecutor(#id, authentication.name)")
    public ResponseEntity<List<CommentResponse>> getCommentsByTask(@Parameter(description = "Task ID") @PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentsByTaskId(id));
    }
}
