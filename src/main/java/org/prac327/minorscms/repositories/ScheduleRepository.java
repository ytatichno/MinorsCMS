package org.prac327.minorscms.repositories;

import org.prac327.minorscms.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ytati
 * on 22.05.2024.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDayOfWeek(String dayOfWeek);
    List<Schedule> findByClassroom(String classroom);
}
