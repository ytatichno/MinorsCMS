package org.prac327.minorscms.controllers;

import lombok.extern.slf4j.Slf4j;
import org.prac327.minorscms.api.Utils;
import org.prac327.minorscms.models.Course;
import org.prac327.minorscms.models.Schedule;
import org.prac327.minorscms.repositories.CompanyRepository;
import org.prac327.minorscms.repositories.CourseRepository;
import org.prac327.minorscms.repositories.ScheduleRepository;
import org.prac327.minorscms.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.IntStream;

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
    CourseRepository courseRepository;
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/course/{id}")
    public String getCourse(@PathVariable("id") Long id, Model model){
        Course course = courseRepository.findById(id).orElseThrow();
        model.addAttribute("course", course);
        Utils.splitSchedulesToModel(scheduleRepository.findByCourse(course), model);
        return "/course";
    }

    @GetMapping("/courses")
    public String getCourses(Model model) throws InterruptedException {
        List<Course> courses = courseRepository.findAll();
//        List<List<Schedule>> schedules = new ArrayList<List<Schedule>>(Collections.nCopies(courses.size(), (List<Schedule>)null));
//        List[] schedules = new List[courses.size()];
        AtomicReferenceArray<List<Schedule>> schedules = new AtomicReferenceArray<>(courses.size());
//        int[] a = new int[]
        Semaphore semaphore = new Semaphore(0);
        IntStream.range(0, courses.size())
                .forEach(
                        i ->
                                scheduleRepository
                                        .findByCourseAsync(courses.get(i))
                                        .thenAccept(r -> {
//                                            log.info(r.toString());
                                            schedules.getAndSet(i, r);
//                                            log.info(schedules.get(i).toString());
                                            semaphore.release();
                                        })
                );
        semaphore.acquire(courses.size());
//        Thread.sleep(100);
//        log.info(schedules.get(3).toString());
        ArrayList<List<Schedule>> syncSchedules = new ArrayList<>(courses.size());
        for(int i = 0; i < courses.size(); i++){
            syncSchedules.add(schedules.get(i));
        }
//        semaphore.setRelease();
//        courses.stream().  .forEach((c, i)->
        model.addAttribute("courses", courses);
        model.addAttribute("schedules", syncSchedules);
        log.warn(syncSchedules.toString());
        return "courseList";
    }





//    @Autowired
//    Students2CoursesRepository students2CoursesRepository;

    //    @GetMapping("/test")
//    public String test(){
//        Student student = new Student();
//        student.setLastname("lastName");
//        student.setName("name");
//        Student savedStudent = studentRepository.save(student);
//        log.warn(savedStudent.toString());
//        return "/test";
//    }
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
    public String test() {

//        log.warn(companyRepository.findByShortname("MS").toString());
//        Student student = studentRepository.findByLastname("Дударенко").orElseThrow();
////        Course course = courseRepository.findByName("Azure").orElseThrow();
//        log.warn(student.toString());
////        log.warn(student.getCourses().toString());
//        Course course = student.getCourses().get(0).getCourse();
//        log.warn(course.getTeachers().toString());
//        log.warn(course.getTeachers().get(0).getTeacher().toString());
//        log.warn;
//        Students2Courses pair = students2CoursesRepository
//                .findById(new StudentCourseId(student.getId(), course.getId()))
//                .orElseThrow();
//        log.warn(pair.toString());
        return "/test";
    }
}
