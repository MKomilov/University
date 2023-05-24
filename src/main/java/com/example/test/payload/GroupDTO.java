package com.example.test.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GroupDTO {
    @NotNull
    private String name;
    @NotNull
    private Integer year;
    @NotNull
    private Integer facultyId;
}
