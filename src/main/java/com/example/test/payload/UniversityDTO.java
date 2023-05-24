package com.example.test.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UniversityDTO {
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private Integer openYear;

}
