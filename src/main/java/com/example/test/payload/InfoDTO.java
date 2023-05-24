package com.example.test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class InfoDTO {
    private Set<GroupStudents> groupStudents;
    private Integer numberOfGroups;
    private Integer totalNumberOfGroups;
}
