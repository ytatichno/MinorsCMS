package org.prac327.minorscms.repositories;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prac327.minorscms.models.Company;
import org.prac327.minorscms.models.Course;
import org.prac327.minorscms.models.Student;
import org.prac327.minorscms.models.Students2Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.prac327.minorscms.repositories.StudentRepositoryTest.getStudent;

/**
 * Created by ytati
 * on 22.05.2024.
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CompanyRepository companyRepository; // Assuming you have a CompanyRepository

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    EntityManager entityManager;

    Company company;

    // Helper method to create a Course with a Company (you might need to modify based on your setup)
    private Course createCourse(String name, String description, String plan, Company company) {
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setPlan(plan);
        course.setCompany(company);
        return course;
    }

    @BeforeEach
    public void init(){
        company = new Company("Test Company", "TC", "Test Description", "Test Address");
        company = companyRepository.save(company);
    }

    @Test
    public void save_ShouldReturnSavedCourse() {
        // Given - Create a Company and Course
        Course course = createCourse("Test Course", "Test Description", "course-plan.pdf", company);

        // When - Perform the action
        Course savedCourse = courseRepository.save(course);

        // Then - Verify the result
        assertNotNull(savedCourse.getId());
//        assertEquals(course, savedCourse);
        assertEquals(course.getId(), savedCourse.getId());
        assertEquals(course.getName(), savedCourse.getName());
        assertEquals(course.getCompany(), savedCourse.getCompany());
        assertEquals(course.getDescription(), savedCourse.getDescription());
        assertEquals(course.getPlan(), savedCourse.getPlan());
        assertEquals(course.getStudents(), savedCourse.getStudents());
        assertEquals(course.getTeachers(), savedCourse.getTeachers());
    }

    @Test
    public void findAll_ShouldReturnAllCourses() {
        // Given - Create some Courses (use @BeforeEach if needed)
        // ...
        List<Course> expected = List.of(
                createCourse("Test course0", "very interesting", null, company),
                createCourse("Test course1", "very interesting", null, company),
                createCourse("Test course2", "very interesting", null, company),
                createCourse("Test course3", "very interesting", null, company)
                );
        courseRepository.saveAll(expected);

        // When - Perform the action
        List<Course> courses = courseRepository.findAll();

        // Then - Verify the result
        assertFalse(courses.isEmpty());
        assertEquals(expected, courses);
        // ... add more assertions based on your specific data
    }

    @Test
    public void findById_ShouldReturnCorrectCourse() {
        // Given - Create a Course and save it

        Course existingCourse = courseRepository.save(createCourse("Test Course", "Test Description", "course-plan.pdf", company));
        Long existingCourseId = existingCourse.getId();

        // When - Perform the action
        Optional<Course> foundCourse = courseRepository.findById(existingCourseId);

        // Then - Verify the result
        assertTrue(foundCourse.isPresent());
        assertEquals(existingCourse, foundCourse.get());
    }

    @Test
    public void findById_ShouldReturnEmptyOptionalWhenNotFound() {
        // Given - Create some Courses (use @BeforeEach if needed)
        // ...


        // When - Perform the action
        Optional<Course> foundCourse = courseRepository.findById(100L); // Assume 100L is an invalid ID

        // Then - Verify the result
        assertFalse(foundCourse.isPresent());
    }

    @Test
    public void findByName_ShouldReturnCorrectCourse() {
        // Given - Create a Course and save it
        Course existingCourse = courseRepository.save(createCourse("Test Course", "Test Description", "course-plan.pdf", company));
        String courseName = existingCourse.getName();

        // When - Perform the action
        Optional<Course> foundCourses = courseRepository.findByName(courseName);

//        Then - Verify the result
        assertTrue(foundCourses.isPresent());
        assertEquals(existingCourse, foundCourses.get());
    }

    @Test
    public void findByCompany_ShouldReturnListOfCompanyCourses(){
        Company anotherCompany = new Company("Another Company", "AC", "Test Description", "Test Address");
        anotherCompany = companyRepository.save(anotherCompany);

        List<Course> expectedFromCompany = List.of(
                createCourse("Test course0", "very interesting", null, company),
                createCourse("Test course1", "very interesting", null, company),
                createCourse("Test course2", "very interesting", null, company),
                createCourse("Test course3", "very interesting", null, company)
                );
        courseRepository.saveAll(expectedFromCompany);

        List<Course> expectedFromAnotherCompany = List.of(
                createCourse("Another Test course0", "very very interesting", null, anotherCompany),
                createCourse("Another Test course1", "very very interesting", null, anotherCompany),
                createCourse("Another Test course2", "very very interesting", null, anotherCompany)
                );
        courseRepository.saveAll(expectedFromAnotherCompany);

        List<Course> actualFromCompany = courseRepository.findCourseByCompanyId(company.getId());
        List<Course> actualFromAnotherCompany = courseRepository.findCourseByCompanyId(anotherCompany.getId());

        assertEquals(expectedFromCompany, actualFromCompany);
        assertEquals(expectedFromAnotherCompany, actualFromAnotherCompany);
    }

    @Test
    public void findStudentsByCourse_ShouldReturnAllStudentsOfCourse(){
        List<Course> courses = List.of(
                createCourse("Test course0", "very interesting", null, company),
                createCourse("Test course1", "very interesting", null, company)
        );
        courseRepository.saveAll(courses);
        List<Student> students = List.of(
                getStudent("Ivan", "Petrov"),
                getStudent("Ivan", "Sidorov"),
                getStudent("Ivan", "Romanov"),
                getStudent("Vladimir", "Romanov")
        );
        studentRepository.saveAll(students);
        List<Students2Courses> pairs = List.of(
                new Students2Courses(students.get(0),courses.get(0)),
                new Students2Courses(students.get(1),courses.get(0)),
                new Students2Courses(students.get(2),courses.get(0)),
                new Students2Courses(students.get(3),courses.get(0)),
                new Students2Courses(students.get(2),courses.get(1)),
                new Students2Courses(students.get(3),courses.get(1))
        );
        pairs.forEach(p->{
            entityManager.persist(p);
            entityManager.refresh(p.getCourse());
            entityManager.refresh(p.getStudent());
        });

        assertEquals(
                new HashSet<>(students),
                courses.get(0).getStudents().stream().map(Students2Courses::getStudent).collect(Collectors.toSet())
        );
        assertEquals(
                new HashSet<>(students.subList(2, 4)),
                courses.get(1).getStudents().stream().map(Students2Courses::getStudent).collect(Collectors.toSet())
        );

    }

    @Test
    public void deleteById_ShouldDeleteCorrectCourse() {
        // Given - Create a Course and save it

        Course existingCourse = courseRepository.save(createCourse("Test Course", "Test Description", "course-plan.pdf", company));
        Long existingCourseId = existingCourse.getId();

        // When - Perform the action
        courseRepository.deleteById(existingCourseId);

        // Then - Verify the result
        Optional<Course> deletedCourse = courseRepository.findById(existingCourseId);
        assertFalse(deletedCourse.isPresent());
    }

}