package com.example.test.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class MarkDTO {
    @Min(value = 1, message = "Mark should be greater than 1")
    @Max(value = 100, message = "Mark should be lower than 100")
    private Integer label;
    @NotNull
    private UUID studentId;
    @NotNull
    private Integer journalId;
    @NotNull
    private Integer subjectId;
}
