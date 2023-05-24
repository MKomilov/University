package com.example.test.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;
}
