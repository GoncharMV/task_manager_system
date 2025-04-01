package com.grettastic.tms.responses;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private String text;
    private Long authorId;
    private Long taskId;
    private LocalDateTime createdAt;
}
