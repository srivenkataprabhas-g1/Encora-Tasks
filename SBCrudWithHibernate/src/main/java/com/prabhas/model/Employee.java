package com.prabhas.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;
import java.util.*;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;

    // One-to-One with Passport
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_id")
    @JsonBackReference(value = "employee-passport")
    private Passport passport;

    // Many-to-One with Department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    @JsonBackReference(value = "department-employees")
    private Department department;

    // One-to-Many with Review
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "employee-reviews")
    private List<Review> reviews = new ArrayList<>();

    // Many-to-Many with Tasks
    @ManyToMany(mappedBy = "employees")
    @JsonBackReference(value = "tasks-employees")
    private Set<Tasks> tasks = new HashSet<>();
}
