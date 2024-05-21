package org.prac327.minorscms.models;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "teachers_courses")
@Data
@NoArgsConstructor
public class Teachers2Courses{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "teach_cour_id")
    @NonNull
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @ToString.Exclude
    @NonNull
    private Course course_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    @ToString.Exclude
    @NonNull
    private Teacher teacher_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teachers2Courses other = (Teachers2Courses) o;
        return Objects.equals(id, other.id)
                && Objects.equals(course_id, other.course_id)
                && Objects.equals(teacher_id, other.teacher_id);
    }
}