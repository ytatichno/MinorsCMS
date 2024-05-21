package org.prac327.minorscms.models;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students_courses")
@Data
@NoArgsConstructor
public class Students2Courses{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "stud_cour_id")
    @NonNull
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    @ToString.Exclude
    @NonNull
    private Course course_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    @ToString.Exclude
    @NonNull
    private Student student_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Students2Courses other = (Students2Courses) o;
        return Objects.equals(id, other.id)
                && Objects.equals(course_id, other.course_id)
                && Objects.equals(student_id, other.student_id);
    }
}