package com.ar.mikellbobadilla.app.post;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

public record PostRequest(
        @NotBlank(message = "Title must be required")
        @Length(min = 5, max = 100)
        String title,
        @NotBlank(message = "Content must be required")
        String content,
        @NotBlank(message = "Category must be required")
        @Length(min = 5, max = 100)
        String category,
        Set<String> tags
) {
}
