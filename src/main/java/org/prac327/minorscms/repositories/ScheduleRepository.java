package org.prac327.minorscms.repositories;

import org.prac327.minorscms.models.Course;
import org.prac327.minorscms.models.Schedule;
import org.prac327.minorscms.models.Teachers2Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by ytati
 * on 22.05.2024.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDayOfWeek(String dayOfWeek);
    List<Schedule> findByClassroom(String classroom);

    @Query("SELECT s FROM Schedule s WHERE s.teachCour.course = ?1")
    List<Schedule> findByCourse(Course course);
    @Async
    @Query("SELECT s FROM Schedule s WHERE s.teachCour.course = ?1")
    CompletableFuture<List<Schedule>> findByCourseAsync(Course course);

    void deleteAllByTeachCour(Teachers2Courses teachCour);
}
