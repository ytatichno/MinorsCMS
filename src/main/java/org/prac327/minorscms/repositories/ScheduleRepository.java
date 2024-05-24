package org.prac327.minorscms.repositories;

import org.prac327.minorscms.models.Course;
import org.prac327.minorscms.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ytati
 * on 22.05.2024.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDayOfWeek(String dayOfWeek);
    List<Schedule> findByClassroom(String classroom);

    @Query("SELECT s FROM Schedule s WHERE s.teach_cour.course = ?1")
    List<Schedule> findByCourse(Course course);
}
