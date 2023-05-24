package com.example.test.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class FacultyDTO {
    @NotNull
    private String name;
    private Integer universityId;
}
