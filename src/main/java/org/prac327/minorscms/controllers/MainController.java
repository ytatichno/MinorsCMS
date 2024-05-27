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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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

    @GetMapping("/")
    public String index(){
        return "redirect:/courses";
    }

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
//        log.warn(syncSchedules.toString());
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

    @GetMapping("/register/company/{id}")
    public String registerCompanyView(@PathVariable("id") Long id, Model model){
        if(id > 0){
            Company company = companyRepository.findById(id).orElseThrow();
            model.addAttribute("id", company.getId());
            model.addAttribute("name", company.getName());
            model.addAttribute("shortname", company.getShortname());
            model.addAttribute("description", company.getDescription());
            model.addAttribute("phone", company.getPhone());
            model.addAttribute("address", company.getAddress());

        }
        return "/registerCompany";
    }
    @PostMapping("/register/company")
    public String registerCompany(Company company, Model model){
        if(company.getId()<=0)
            company.setId(null);
        companyRepository.save(company);

        return "redirect:/company/" + company.getId();
    }
    @GetMapping("/register/teacher/{id}")
    public String registerTeacherView(@PathVariable("id") Long id, Model model){
        if(id > 0){
            Teacher teacher = teacherRepository.findById(id).orElseThrow();
            model.addAttribute("id", teacher.getId());
            model.addAttribute("lastname", teacher.getLastname());
            model.addAttribute("name", teacher.getName());
            model.addAttribute("fathername", teacher.getFathername());
            model.addAttribute("phone", teacher.getPhone());
            model.addAttribute("mail", teacher.getMail());
            model.addAttribute("phone", teacher.getPhone());
            model.addAttribute("education", teacher.getEducation());
            model.addAttribute("company", teacher.getCompany());

        }
        model.addAttribute("companies", companyRepository.findAll());
        return "/registerTeacher";
    }
    @PostMapping("/register/teacher")
    public String registerTeacher(Teacher teacher, Model model){
        if(teacher.getId()<=0)
            teacher.setId(null);
        teacherRepository.save(teacher);

        return "redirect:/teacher/" + teacher.getId();
    }
    @GetMapping("/register/student/{id}")
    public String registerStudentView(@PathVariable("id") Long id, Model model){
        if(id > 0){
            Student student = studentRepository.findById(id).orElseThrow();
            model.addAttribute("id", student.getId());
            model.addAttribute("lastname", student.getLastname());
            model.addAttribute("name", student.getName());
            model.addAttribute("fathername", student.getFathername());
            model.addAttribute("phone", student.getPhone());

        }
        return "/registerStudent";
    }
    @PostMapping("/register/student")
    public String registerStudent(Student student, Model model){
        if(student.getId() <= 0)
            student.setId(null);
        studentRepository.save(student);

        return "redirect:/student/" + student.getId();
    }

    @GetMapping("/register/course/{id}")
    public String registerCourseView(@PathVariable("id") Long id, Model model){
        if(id > 0){
            Course course = courseRepository.findById(id).orElseThrow();
            model.addAttribute("id", course.getId());
            model.addAttribute("name", course.getName());
            model.addAttribute("company", course.getCompany());
            model.addAttribute("description", course.getDescription());
            model.addAttribute("plan", course.getPlan());


        }
        model.addAttribute("companies", companyRepository.findAll());
        return "/registerCourse";
    }
    @PostMapping("/register/course")
    public String registerCourse(Course course, Model model) {
        if (course.getId() <= 0)
            course.setId(null);
//        if(course.)
        courseRepository.save(course);

        return "redirect:/course/" + course.getId();
    }

    @GetMapping("/relationManager")
    public String relationManager(@RequestParam(required = false) Long teacher,
                                  @RequestParam(required = false) Long student,
                                  @RequestParam(required = false) Long course,
                                  Model model) {
        if (teacher != null && student != null && course != null)
            throw new ConcurrentModificationException("Two many entities for relation Manager");
        model.addAttribute("courses", courseRepository.findAll());
        if (teacher != null) {
            if (teacher > 0)
                model.addAttribute("teacher", teacherRepository.findById(teacher).orElseThrow());
            model.addAttribute("teachers", teacherRepository.findAll());
        } else if (student != null) {
            if (student > 0)
                model.addAttribute("student", studentRepository.findById(student).orElseThrow());
            model.addAttribute("students", studentRepository.findAll());
        }
        if (course != null && course > 0)
            model.addAttribute("course", courseRepository.findById(course).orElseThrow());

        return "/relationManager";
    }
    @GetMapping("/scheduleManager")
    public String scheduleManager(@RequestParam(required = false) Long course, Model model){
        Course c = courseRepository.findById(course).orElseThrow();
        model.addAttribute("course", c);
        Utils.splitSchedulesToModel(scheduleRepository.findByCourse(c), model);
        model.addAttribute("teachers", c.getTeachers());
        return "/scheduleManager";

    }


    @GetMapping("/test")
    public String test() {

        return "/test";
    }
}
