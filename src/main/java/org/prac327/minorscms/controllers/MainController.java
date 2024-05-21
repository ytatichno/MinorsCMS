package org.prac327.minorscms.controllers;

import lombok.extern.slf4j.Slf4j;
import org.prac327.minorscms.models.Schedule;
import org.prac327.minorscms.models.Student;
import org.prac327.minorscms.repositories.ScheduleRepository;
import org.prac327.minorscms.repositories.StudentRepository;
import org.prac327.minorscms.repositories.Students2CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by ytati
 * on 22.05.2024.
 */
@Controller
@Slf4j
public class MainController {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    Students2CoursesRepository students2CoursesRepository;

//    @GetMapping("/test")
//    public String test(){
//        Student student = new Student();
//        student.setLastname("lastName");
//        student.setName("name");
//        Student savedStudent = studentRepository.save(student);
//        log.warn(savedStudent.toString());
//        return "/test";
//    }
    @GetMapping("/test")
    public String test(){
        Student student = new Student();
        student.setLastname("lastName");
        student.setName("name");
        Student savedStudent = studentRepository.save(student);
        log.warn(savedStudent.toString());
        return "/test";
    }
}
