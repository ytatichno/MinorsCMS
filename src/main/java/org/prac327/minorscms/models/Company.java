package org.prac327.minorscms.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

    @Column(name = "shortname", unique = true, nullable = false)
    @NonNull
    private String shortname;


    @Column(nullable = false, name = "description")
    @NonNull
    private String description;


    @Column(name = "phone")
    private String phone;

    @Column(nullable = false, name = "address")
    @NonNull
    private String address;

    public Company(String name, String shortname, String description, String address) {
        this.name = name;
        this.shortname = shortname;
        this.description = description;
        this.address = address;
    }


}