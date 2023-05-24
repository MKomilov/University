package com.example.test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentMark {
    private String studentName;
    private Integer totalScore;
}
