package com.example.test.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NameDTO {
    @NotNull
    private String name;
}
