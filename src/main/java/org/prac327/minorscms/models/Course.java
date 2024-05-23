package org.prac327.minorscms.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    @NonNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @ToString.Exclude
    @NonNull
    private Company company;

    @Column(name = "photo")
    private String photo;

    @Column(nullable = false, name = "description")
    @NonNull
    private String description;

    @Column(name = "plan")
    private String plan;  // url to file with course plan

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
    List<Students2Courses> students;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
    List<Teachers2Courses> teachers;

}