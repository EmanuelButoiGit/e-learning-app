package com.emanuel.starterlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricDto {
    @NotBlank
    @Positive
    private String name;

    @NotBlank
    @Positive
    private String description;

    @NotBlank
    @Positive
    private String emoji;
}
