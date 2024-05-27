package org.prac327.minorscms.controllers;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.prac327.minorscms.models.Students2Courses;
import org.prac327.minorscms.models.Teachers2Courses;
import org.prac327.minorscms.repositories.CourseRepository;
import org.prac327.minorscms.repositories.ScheduleRepository;
import org.prac327.minorscms.repositories.StudentRepository;
import org.prac327.minorscms.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by ytati
 * on 27.05.2024.
 */
@RestController
@RequestMapping("/relate")
@Transactional
public class RelationController {
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

    @GetMapping("/t2c/{teacherId}/{courseId}")
    public ResponseEntity<?> checkT2CRelation(@PathVariable Long teacherId, @PathVariable Long courseId){
        List<Teachers2Courses> relation = entityManager
                .createQuery("FROM Teachers2Courses t2c " +
                                    "WHERE t2c.teacher.id = :teacherId AND t2c.course.id = :courseId"
                        , Teachers2Courses.class)
                .setParameter("teacherId", teacherId)
                .setParameter("courseId", courseId)
                .getResultList();
        if(relation.isEmpty() || relation.get(0) == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.FOUND).body(relation.get(0).getId());
    }
}
