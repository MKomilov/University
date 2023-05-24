package com.example.test.entity;

import com.example.test.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Journal extends BaseEntity {
    @JsonIgnore
    @ManyToOne
    private Group group;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Subject> subjects;

}
