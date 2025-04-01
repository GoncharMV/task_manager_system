package com.grettastic.tms.model;

import com.grettastic.tms.enums.TaskPriority;
import com.grettastic.tms.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tasks")
@Schema(description = "Entity representing a task in the database")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the task", example = "1")
    private Long id;

    @Schema(description = "Title of the task", example = "Fix bug in login page")
    private String title;

    @Schema(description = "Description of the task", example = "There is an issue with the login page that causes the page to crash.")
    private String description;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status of the task", example = "IN_PROGRESS")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Priority of the task", example = "LOW")
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @Schema(description = "Creator of the task")
    private User author;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    @Schema(description = "Executor, assigned to the task")
    private User executor;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @Schema(description = "List of comments to the task")
    private List<Comment> comments;

}
