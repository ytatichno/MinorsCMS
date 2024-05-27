package org.prac327.minorscms.controllers;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.prac327.minorscms.models.Schedule;
import org.prac327.minorscms.models.Teachers2Courses;
import org.prac327.minorscms.repositories.CourseRepository;
import org.prac327.minorscms.repositories.ScheduleRepository;
import org.prac327.minorscms.repositories.StudentRepository;
import org.prac327.minorscms.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ytati
 * on 27.05.2024.
 */
@RestController
@RequestMapping("/schedule")
@Transactional
@Slf4j
public class ScheduleController {
    @Autowired
    EntityManager entityManager;

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    @PostMapping("/post")
    public ResponseEntity<?> addSchedule(String dayOfWeek, String start_time, String end_time,
                                         String classroom, Long teachCour) {
//
        Teachers2Courses t2c = entityManager.createQuery("FROM Teachers2Courses t2c WHERE t2c.id = :teachCour",
                        Teachers2Courses.class)
                .setParameter("teachCour", teachCour)
                .getSingleResult();
        Schedule schedule = new Schedule();
        schedule.setDayOfWeek(dayOfWeek);
        schedule.setStart_time(start_time);
        schedule.setEnd_time(end_time);
        schedule.setClassroom(classroom);
        schedule.setTeachCour(t2c);
        scheduleRepository.save(schedule);
        return ResponseEntity.ok(schedule.getId());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow();
        entityManager.remove(schedule);
        return ResponseEntity.ok(schedule);
    }
}
