package com.grettastic.tms.requests;

import com.grettastic.tms.enums.TaskPriority;
import com.grettastic.tms.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body to create or update a task")
public class TaskRequest {
    @Schema(description = "Title of the task", example = "Fix bug in login page")
    @Size(min = 2, max = 250)
    @NotBlank(message = "Title cannot be empty")
    @NotNull(message = "Title cannot be null")
    private String title;

    @Schema(description = "Priority of the task", example = "LOW")
    private TaskPriority priority;

    @Schema(description = "Description of the task", example = "There is an issue with the login page that causes the page to crash.")
    @Size(min = 20, max = 2000)
    @NotBlank(message = "Description cannot be empty")
    @NotNull(message = "Description cannot be null")
    private String description;

    @Schema(description = "Executor id, assigned to the task")
    private Long executorId;

    @Schema(description = "Status of the task", example = "IN_PROGRESS")
    private TaskStatus status;
}
