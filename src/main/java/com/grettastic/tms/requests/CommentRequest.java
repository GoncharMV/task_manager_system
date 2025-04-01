package com.grettastic.tms.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    @Size(min = 20, max = 2000)
    @NotBlank(message = "Text cannot be empty")
    private String text;
}
