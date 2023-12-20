package com.example.tastifybackend.domain.category.dto;

import com.example.tastifybackend.validation.markers.OnPost;
import com.example.tastifybackend.validation.markers.OnPut;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class CategoryInput {
    private String id;
    @NotBlank(message = "{NOT_BLANK}", groups = {OnPost.class, OnPut.class})
    private String value;
}
