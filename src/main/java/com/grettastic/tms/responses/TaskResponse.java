package com.grettastic.tms.responses;

import com.grettastic.tms.enums.TaskPriority;
import com.grettastic.tms.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response containing task details")
public class TaskResponse {
    @Schema(description = "Unique identifier of the task", example = "1")
    private Long id;

    @Schema(description = "Title of the task", example = "Fix bug in login page")
    private String title;

    @Schema(description = "Description of the task", example = "There is an issue with the login page that causes the page to crash.")
    private String description;

    @Schema(description = "Status of the task", example = "IN_PROGRESS")
    private TaskStatus status;

    @Schema(description = "Priority of the task", example = "LOW")
    private TaskPriority priority;

    @Schema(description = "Creator id of the task")
    private Long authorId;

    @Schema(description = "Executor id, assigned to the task")
    private Long executorId;

    @Schema(description = "List of comments to the task")
    private List<CommentResponse> comments;
}
