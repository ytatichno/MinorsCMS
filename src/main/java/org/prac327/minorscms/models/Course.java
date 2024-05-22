package org.prac327.minorscms.models;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "course_id")
    @NonNull
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    @NonNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @ToString.Exclude
    @NonNull
    private Company company_id;

    @Column(name = "photo")
    private String photo;

    @Column(nullable = false, name = "description")
    @NonNull
    private String description;

    @Column(name = "plan")
    private String plan;  // url to file with course plan

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course other = (Course) o;
        return Objects.equals(id, other.id)
                && name.equals(other.name)
                && Objects.equals(company_id, other.company_id)
                && photo.equals(other.photo)
                && description.equals(other.description)
                && Objects.equals(plan, other.plan);
    }
}