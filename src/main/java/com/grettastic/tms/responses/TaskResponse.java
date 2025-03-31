package com.grettastic.tms.responses;

import com.grettastic.tms.enums.TaskPriority;
import com.grettastic.tms.enums.TaskStatus;
import com.grettastic.tms.model.Comment;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {
    private Long id;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private UserResponse author;
    private UserResponse executor;
    private List<CommentResponse> comments;
}
