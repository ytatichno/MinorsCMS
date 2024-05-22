package org.prac327.minorscms.models;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    @NonNull
    private String name;

    @Column(name = "short_name", unique = true)
    @NonNull
    private String short_name;

    @Column(name = "photo")
    private String photo;

    @Column(nullable = false, name = "description")
    @NonNull
    private String description;


    @Column(name = "phone")
    private String phone;

    @Column(nullable = false, name = "address")
    @NonNull
    private String address;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company other = (Company) o;
        return Objects.equals(id, other.id)
                && name.equals(other.name)
                && short_name.equals(other.short_name)
                && photo.equals(other.photo)
                && description.equals(other.description)
                && phone.equals(other.phone)
//                && mail.equals(other.mail)
                && address.equals(other.address);
    }
}