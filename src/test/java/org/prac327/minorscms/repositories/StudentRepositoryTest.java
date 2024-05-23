package org.prac327.minorscms.repositories;

import org.junit.jupiter.api.Test;
import org.prac327.minorscms.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by ytati
 * on 23.05.2024.
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

//    @Autowired
//    private EntityManager entityManager;

    @Test
    void save_ShoudReturnSavedStudent() {
        // Create a sample Student entity
        Student student = new Student();
        student.setLastname("Doe");
        student.setName("John");
        student.setFathername("James");
        // ... (Set other fields as needed)

        // Save the student
        Student savedStudent = studentRepository.save(student);

        // Assert that the student is saved correctly
        assertNotNull(savedStudent.getId());
        assertEquals(student, savedStudent);
    }

    @Test
    void findAll_ShouldReturnAllStudents() {
        // Create some sample students and save them
        // ...
        List<Student> expected = List.of(
                getStudent("Ivan", "Petrov"),
                getStudent("Ivan", "Sidorov"),
                getStudent("Ivan", "Romanov"),
                getStudent("Vladimir", "Romanov")
        );
        studentRepository.saveAll(expected);
        // Find all students
        List<Student> students = studentRepository.findAll();

        // Assert that the list contains the expected students
        assertFalse(students.isEmpty());
        assertEquals(expected, students);
        // ... (Add specific assertions based on your test data)
    }

    @Test
    void findById_ShouldReturnStudentWithSpecifiedId() {
        // Create a sample student and save it
        Student student = getStudent("Ivan", "Ivanov");
        // ... (Set the necessary fields)
        Student savedStudent = studentRepository.save(student);

        // Find the student by its ID
        Optional<Student> foundStudent = studentRepository.findById(savedStudent.getId());

        // Assert that the student is found correctly
        assertTrue(foundStudent.isPresent());
        assertEquals(savedStudent, foundStudent.get());
    }

    @Test
    void findByLastName_ShouldReturnStudentWithSameLastname() {
        // Create some sample students with different last names and save them
        // ...
        List<Student> expected = List.of(
                getStudent("Ivan", "Petrov"),
                getStudent("Ivan", "Sidorov"),
                getStudent("Ivan", "Romanov"),
                getStudent("Vladimir", "Romanov")
        );
        studentRepository.saveAll(expected);
        // Find students by a specific last name
        String lastName = "Romanov"; // Replace with your desired last name
        List<Student> students = studentRepository.findByLastname(lastName);

        // Assert that the list contains the expected students
        assertFalse(students.isEmpty());
        students.forEach(s->assertEquals(s.getLastname(), lastName));
        // ... (Add specific assertions based on your test data)
    }

    @Test
    void deleteStudentById() {
        // Create a sample student and save it
        Student student = getStudent("Ivan", "Ivanov");
        // ... (Set the necessary fields)
        Student savedStudent = studentRepository.save(student);

        // Delete the student
        studentRepository.deleteById(savedStudent.getId());

        // Assert that the student is deleted
        Optional<Student> deletedStudent = studentRepository.findById(savedStudent.getId());
        assertTrue(deletedStudent.isEmpty());
    }

    private static Student getStudent(String name, String lastname) {
        Student student = new Student();
        student.setName(name);
        student.setLastname(lastname);
        return student;
    }
}