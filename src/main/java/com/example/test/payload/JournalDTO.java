package com.example.test.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class JournalDTO {
    @NotNull
    private String name;
    @NotNull
    private Integer groupId;
    private Set<Integer> subjectIds;
}
