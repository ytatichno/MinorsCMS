package org.prac327.minorscms.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.sql.Time;

@Entity
@Table(name = "schedule")
@Data
@NoArgsConstructor
public class Schedule  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


}