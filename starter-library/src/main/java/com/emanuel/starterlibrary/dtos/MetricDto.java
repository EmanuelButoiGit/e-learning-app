package com.emanuel.starterlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricDto {
    @NotBlank
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    private String description;

    @NotBlank
    @NotEmpty
    private String emoji;
}
