package com.grettastic.tms.responses;

import com.grettastic.tms.enums.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private List<Long> createdTasksIds;
    private List<Long> assignedTasksIds;
    private List<Long> commentsIds;
}
