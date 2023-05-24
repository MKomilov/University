package com.example.test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupStudents {
    private String groupName;
    private Integer numberOfStudents;
}
