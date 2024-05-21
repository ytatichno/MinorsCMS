package org.prac327.minorscms.models;

import lombok.*;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false, name = "student_id")
//    @NonNull
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


    @Column(name = "photo")
    private String photo;


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