package org.prac327.minorscms.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "lastname")
    @NonNull
    private String lastname;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(name = "fathername")
    private String fathername;

    @Column(name = "education")
    private String education;

    @Column(name = "mail")
    private String mail;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @ToString.Exclude
    @NonNull
    private Company company;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "teacher", fetch = FetchType.LAZY)
    List<Teachers2Courses> courses;

}