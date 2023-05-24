package com.example.test.entity;

import com.example.test.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class University extends BaseEntity {
    private String address;
    private Integer openYear;

}
