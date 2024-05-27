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

        return "(" + getPrettyStartTime() + " - " + getPrettyEndTime() + ")";
    }

    public String getPrettyStartTime(){
        return (start_time.getHours() == 0 ? "00" :start_time.getHours())
                + ":"
                + (start_time.getMinutes() == 0 ? "00" :start_time.getMinutes());
    }
    public String getPrettyEndTime(){
        return (end_time.getHours() == 0 ? "00" :end_time.getHours())
                + ":"
                + (end_time.getMinutes() == 0 ? "00" :end_time.getMinutes());
    }

//    public void setStart_time(Time start_time) {
//        this.start_time = start_time;
//    }
//
//    public void setEnd_time(Time end_time) {
//        this.end_time = end_time;
//    }

    public void setStart_time(String start_time) {
//        String[] split = start_time.split(":");
//        this.start_time = new Time(Integer.parseInt(split[0]), Integer.parseInt(split[1]),0);
        this.start_time = Time.valueOf(start_time + ":0");
    }

    public void setEnd_time(String end_time) {
        this.end_time = Time.valueOf(end_time + ":0");
    }
}