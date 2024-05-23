package org.prac327.minorscms.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
public class Student {

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


    @Column(name = "phone")
    private String phone;


    @OneToMany(cascade=CascadeType.ALL, mappedBy = "student", fetch = FetchType.LAZY)
    List<Students2Courses> courses;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student other = (Student) o;
        return Objects.equals(id, other.id)
                && lastname.equals(other.lastname)
                && name.equals(other.name)
                && fathername.equals(other.fathername)
//                && mail.equals(other.mail)
                && phone.equals(other.phone)
//                && Objects.equals(birthday, other.birthday)
                && photo.equals(other.photo);
    }
}