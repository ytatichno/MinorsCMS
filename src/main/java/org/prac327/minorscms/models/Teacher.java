package org.prac327.minorscms.models;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "teacher_id")
    @NonNull
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
    private Company company_id;

    @Column(name = "photo")
    private String photo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher other = (Teacher) o;
        return Objects.equals(id, other.id)
                && lastname.equals(other.lastname)
                && name.equals(other.name)
                && fathername.equals(other.fathername)
                && education.equals(other.education)
                && mail.equals(other.mail)
                && phone.equals(other.phone)
                && Objects.equals(company_id, other.company_id)
                && photo.equals(other.photo);
    }
}