package com.grettastic.tms.requests;

import com.grettastic.tms.enums.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private TaskPriority priority;
    private String description;
    private Long executorId;

}
