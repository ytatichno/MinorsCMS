package org.prac327.minorscms.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prac327.minorscms.models.Company;
import org.prac327.minorscms.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by ytati
 * on 24.05.2024.
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    CompanyRepository companyRepository;

//    @Autowired
//    private EntityManager entityManager;
    Company company = null;
    @BeforeEach
    void init(){
        company = new Company("Test Company", "TC", "Test Description", "Test Address");
        company = companyRepository.save(company);
    }

    @Test
    void save_ShoudReturnSavedTeacher() {
        // Create a sample Teacher entity
        Teacher teacher = new Teacher();
        teacher.setLastname("Doe");
        teacher.setName("John");
        teacher.setFathername("James");
        teacher.setMail("pochta@mail.ru");
        teacher.setPhone("+78897878975");
        teacher.setEducation("MIT");
        // ... (Set other fields as needed)

        // Save the teacher
        Teacher savedTeacher = teacherRepository.save(teacher);

        // Assert that the teacher is saved correctly
        assertNotNull(savedTeacher.getId());
//        assertEquals(teacher, savedTeacher);
        assertEquals(teacher.getId(),savedTeacher.getId());
        assertEquals(teacher.getMail(),savedTeacher.getMail());
        assertEquals(teacher.getLastname(),savedTeacher.getLastname());
        assertEquals(teacher.getName(),savedTeacher.getName());
        assertEquals(teacher.getCourses(),savedTeacher.getCourses());
        assertEquals(teacher.getEducation(),savedTeacher.getEducation());
        assertEquals(teacher.getPhone(),savedTeacher.getPhone());
        assertEquals(teacher.getFathername(),savedTeacher.getFathername());
        assertEquals(teacher.getCompany(),savedTeacher.getCompany());
    }

    @Test
    void findAll_ShouldReturnAllTeachers() {
        // Create some sample teachers and save them
        // ...
        List<Teacher> expected = List.of(
                getTeacher("Ivan", "Petrov", company),
                getTeacher("Ivan", "Sidorov", company),
                getTeacher("Ivan", "Romanov", company),
                getTeacher("Vladimir", "Romanov", company)
        );
        teacherRepository.saveAll(expected);
        // Find all teachers
        List<Teacher> teachers = teacherRepository.findAll();

        // Assert that the list contains the expected teachers
        assertFalse(teachers.isEmpty());
        assertEquals(expected, teachers);
        // ... (Add specific assertions based on your test data)
    }

    @Test
    void findById_ShouldReturnTeacherWithSpecifiedId() {
        // Create a sample teacher and save it
        Teacher teacher = getTeacher("Ivan", "Ivanov", company);
        // ... (Set the necessary fields)
        Teacher savedTeacher = teacherRepository.save(teacher);

        // Find the teacher by its ID
        Optional<Teacher> foundTeacher = teacherRepository.findById(savedTeacher.getId());

        // Assert that the teacher is found correctly
        assertTrue(foundTeacher.isPresent());
        assertEquals(savedTeacher, foundTeacher.get());
    }
    
    @Test
    void findByCompanyShortname_ShouldReturnAllTeachersFromCompany(){
        Company anotherCompany = new Company("Another Company", "AC", "Test Description", "Test Address");
        companyRepository.save(anotherCompany);
        // Create a sample teacher and save it
        List<Teacher> expected = List.of(
                getTeacher("Ivan", "Petrov", company),
                getTeacher("Ivan", "Sidorov", company),
                getTeacher("Ivan", "Romanov", anotherCompany),
                getTeacher("Vladimir", "Romanov", anotherCompany)
        );
        teacherRepository.saveAll(expected);

        // Find the teacher by its ID
        List<Teacher> teachersTC = teacherRepository.findByCompanyShortname("TC");
        List<Teacher> teachersAC = teacherRepository.findByCompanyShortname("AC");

        // Assert that the teacher is found correctly
        assertFalse(teachersTC.isEmpty());
        assertEquals(expected.subList(0,2), teachersTC);

        assertFalse(teachersAC.isEmpty());
        assertEquals(expected.subList(2,4), teachersAC);
    }

    @Test
    void findByLastName_ShouldReturnTeacherWithSameLastname() {
        // Create some sample teachers with different last names and save them
        // ...
        List<Teacher> expected = List.of(
                getTeacher("Ivan", "Petrov", company),
                getTeacher("Ivan", "Sidorov", company),
                getTeacher("Ivan", "Romanov", company),
                getTeacher("Vladimir", "Romanov", company)
        );
        teacherRepository.saveAll(expected);
        // Find teachers by a specific last name
        String lastName = "Romanov"; // Replace with your desired last name
        List<Teacher> teachers = teacherRepository.findByLastname(lastName);

        // Assert that the list contains the expected teachers
        assertFalse(teachers.isEmpty());
        teachers.forEach(s->assertEquals(s.getLastname(), lastName));
        // ... (Add specific assertions based on your test data)
    }

    @Test
    void deleteTeacherById() {
        // Create a sample teacher and save it
        Teacher teacher = getTeacher("Ivan", "Ivanov", company);
        // ... (Set the necessary fields)
        Teacher savedTeacher = teacherRepository.save(teacher);

        // Delete the teacher
        teacherRepository.deleteById(savedTeacher.getId());

        // Assert that the teacher is deleted
        Optional<Teacher> deletedTeacher = teacherRepository.findById(savedTeacher.getId());
        assertTrue(deletedTeacher.isEmpty());
    }

    private static Teacher getTeacher(String name, String lastname, Company company) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setLastname(lastname);
        teacher.setCompany(company);
        return teacher;
    }
}