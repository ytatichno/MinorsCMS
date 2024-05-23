package org.prac327.minorscms.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students_courses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "course_id"})
        }
)
@Getter
@Setter
//@ToString
@NoArgsConstructor
public class Students2Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stud_cour_id", unique = true, nullable = false)
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    @NonNull
    private Student student;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    @ToString.Exclude
    @NonNull
    private Course course;

    public Students2Courses(@NonNull Student student, @NonNull Course course) {
        this.student = student;
        this.course = course;
    }
}