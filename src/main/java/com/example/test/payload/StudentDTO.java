package com.example.test.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class StudentDTO {
    @NotNull
    private UUID studentId;
    @NotNull
    private Integer groupId;
}
