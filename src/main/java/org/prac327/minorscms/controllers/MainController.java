package org.prac327.minorscms.controllers;

import lombok.extern.slf4j.Slf4j;
import org.prac327.minorscms.api.Utils;
import org.prac327.minorscms.models.*;
import org.prac327.minorscms.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/course/{id}")
    public String getCourse(@PathVariable("id") Long id, Model model){
        Course course = courseRepository.findById(id).orElseThrow();
        model.addAttribute("course", course);
        Utils.splitSchedulesToModel(scheduleRepository.findByCourse(course), model);
        return "/course";
    }

    @GetMapping("/company/{id}")
    public String getCompany(@PathVariable("id") Long id, Model model){
        Company company = companyRepository.findById(id).orElseThrow();
        model.addAttribute("company", company);
        model.addAttribute("courses", courseRepository.findCourseByCompanyId(company.getId()));
        List<Teacher> teachers = teacherRepository.findByCompanyShortname(company.getShortname());
        model.addAttribute("teachers", teachers);
        return "/company";
    }

    @GetMapping("/companies")
    public String getCompanies(Model model){
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        List<Integer> courseCount = new ArrayList<>(companies.size());
        companies.forEach(c->courseCount.add(courseRepository.countCoursesByCompanyId(c.getId())));
        model.addAttribute("courseCount", courseCount);
        return "/companyList";

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

    @GetMapping("/teacher/{id}")
    public String getTeacher(@PathVariable("id") Long id, Model model){
        Teacher teacher = teacherRepository.findById(id).orElseThrow();
        model.addAttribute("teacher", teacher);
        return "/teacher";
    }
    @GetMapping("/teachers")
    public String getTeachers(Model model) {
        List<Teacher> teachers = teacherRepository.findAll();
        model.addAttribute("teachers", teachers);
        return "/teacherList";
    }
    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable("id") Long id, Model model){
        Student student = studentRepository.findById(id).orElseThrow();
        model.addAttribute("student", student);
        return "/student";
    }

    @GetMapping("/students")
    public String getStudents(Model model){
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "/studentList";
    }

    @GetMapping("/register/company")
    public String registerCompanyView(Model model){
        return "/registerCompany";
    }
    @PostMapping("/register/company")
    public String registerCompany(Company company, Model model){
        companyRepository.save(company);

        return "redirect:/company/" + company.getId();
    }




    @GetMapping("/test")
    public String test() {

        return "/test";
    }
}
