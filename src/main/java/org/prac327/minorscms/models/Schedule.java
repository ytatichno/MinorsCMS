package org.prac327.minorscms.models;

import lombok.*;

import jakarta.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "schedule")
@Data
@NoArgsConstructor
public class Schedule  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "schedule_id")
    @NonNull
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teach_cour")
    @ToString.Exclude
    @NonNull
    private Teachers2Courses teach_cour;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(nullable = false, name = "start_time")
    @NonNull
    private Time start_time;

    @Column(nullable = false, name = "end_time")
    @NonNull
    private Time end_time;

    @Column(nullable = false, name = "classroom")
    @NonNull
    private String classroom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule other = (Schedule) o;
        return Objects.equals(id, other.id)
                && Objects.equals(teach_cour, other.teach_cour)
//                && Objects.equals(date, other.date)
                && Objects.equals(start_time, other.start_time)
                && Objects.equals(end_time, other.end_time)
                && classroom.equals(other.classroom);
    }
}