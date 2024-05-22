package org.prac327.minorscms.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "teachers_courses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"teacher_id", "course_id"})
        }
)
@Data
@NoArgsConstructor
public class Teachers2Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "teach_cour_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    @ToString.Exclude
    @NonNull
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", nullable = false)
    @ToString.Exclude
    @NonNull
    private Teacher teacher;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teachers2Courses other = (Teachers2Courses) o;
        return Objects.equals(id, other.id);
//                && Objects.equals(course_id, other.course_id)
//                && Objects.equals(teacher_id, other.teacher_id);
    }
}