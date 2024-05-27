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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "teach_cour")
    @ToString.Exclude
    @NonNull
    private Teachers2Courses teachCour;

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

//    @SuppressWarnings
    public String getInterval(){
        return "(" + start_time.getHours() + ":" + start_time.getMinutes() + " - " + end_time.getHours() + ":" + end_time.getMinutes() + ")";
    }


}