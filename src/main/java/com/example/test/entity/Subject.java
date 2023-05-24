package com.example.test.entity;

import com.example.test.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject extends BaseEntity {
    @JsonIgnore
    @ManyToMany(mappedBy = "subjects")
    private Set<Journal> journals;
}
