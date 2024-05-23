package org.prac327.minorscms.repositories;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prac327.minorscms.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by ytati
 * on 23.05.2024.
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ScheduleRepositoryTest {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EntityManager entityManager;

    Teachers2Courses tcStub = null;

    @BeforeEach
    void init(){
        Company companyStub = new Company();
        companyStub.setName("com");
        companyStub.setAddress("add");
        companyStub.setShortname("c");
        companyStub.setDescription("d");
        companyRepository.save(companyStub);


        Teacher teacherStub = new Teacher();
        teacherStub.setName("te");
        teacherStub.setLastname("acher");
        teacherStub.setCompany(companyStub);
        teacherRepository.save(teacherStub);

        Course courseStub = new Course();
        courseStub.setName("best course");
        courseStub.setCompany(companyStub);
        courseStub.setDescription("very interesting");
        courseRepository.save(courseStub);

        tcStub = new Teachers2Courses();
        tcStub.setCourse(courseStub);
        tcStub.setTeacher(teacherStub);

        entityManager.persist(tcStub);


    }

    private Schedule createSchedule(Teachers2Courses teach_cour, String classroom, String dayOfWeek, Time start_time, Time end_time) {
        Schedule schedule = new Schedule();
        schedule.setTeach_cour(teach_cour);
        schedule.setClassroom(classroom);
        schedule.setDayOfWeek(dayOfWeek);
        schedule.setStart_time(start_time);
        schedule.setEnd_time(end_time);
        return schedule;
    }

    @Test
    void save_ShouldReturnSavedSchedule() {
        // Create a sample Schedule entity
        Schedule schedule = createSchedule(tcStub, "001", "Monday", Time.valueOf("8:45:0"), Time.valueOf("10:20:0") );
        // Set the necessary fields (e.g., teach_cour, dayOfWeek, start_time, end_time, classroom)
        // ...

        // Save the schedule
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Assert that the schedule is saved correctly
        assertNotNull(savedSchedule.getId());
        assertEquals(savedSchedule, schedule);
    }

    @Test
    void findAll_ShouldReturnAllSchedules() {

        System.out.println(courseRepository.findAll().get(0).getTeachers());
        // Create some sample schedules and save them
        // ...
        List<Schedule> expected = List.of(
                createSchedule(tcStub, "001", "Monday", Time.valueOf("8:45:0"), Time.valueOf("10:20:0") ),
                createSchedule(tcStub, "002", "Monday", Time.valueOf("8:45:0"), Time.valueOf("10:20:0") ),
                createSchedule(tcStub, "P14", "Tuesday", Time.valueOf("10:30:0"), Time.valueOf("12:0:0") )
        );
        scheduleRepository.saveAll(expected);

        // Find all schedules
        List<Schedule> schedules = scheduleRepository.findAll();

        // Assert that the list contains the expected schedules
        assertFalse(schedules.isEmpty());
        assertEquals(expected, schedules);
        // ... (Add specific assertions based on your test data)
    }

    @Test
    void findById_ShouldReturnCorrectSchedule() {
        // Create a sample schedule and save it
        Schedule schedule = createSchedule(tcStub, "001", "Monday", Time.valueOf("8:45:0"), Time.valueOf("10:20:0") );
        // ... (Set the necessary fields)
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Find the schedule by its ID
        Optional<Schedule> foundSchedule = scheduleRepository.findById(savedSchedule.getId());

        // Assert that the schedule is found correctly
        assertTrue(foundSchedule.isPresent());
        assertEquals(savedSchedule, foundSchedule.get());
    }

    @Test
    void findByDayOfWeek_ShouldReturnAllSchedulesByDayOfWeek() {
        // Create some sample schedules with different days of the week and save them
        // ...
        List<Schedule> expected = List.of(
                createSchedule(tcStub, "001", "Monday", Time.valueOf("8:45:0"), Time.valueOf("10:20:0") ),
                createSchedule(tcStub, "002", "Monday", Time.valueOf("8:45:0"), Time.valueOf("10:20:0") ),
                createSchedule(tcStub, "P14", "Tuesday", Time.valueOf("10:30:0"), Time.valueOf("12:0:0") )
        );
        scheduleRepository.saveAll(expected);
        // Find schedules by a specific day of the week
        // Assert that the list contains the expected schedules
        List<Schedule> schedules = scheduleRepository.findByDayOfWeek("Monday");
        assertFalse(schedules.isEmpty());
        assertEquals(expected.subList(0,2), schedules);

        schedules = scheduleRepository.findByDayOfWeek("Tuesday");
        assertFalse(schedules.isEmpty());
        assertEquals(expected.subList(2,3), schedules);

        schedules = scheduleRepository.findByDayOfWeek("Wednesday");
        assertTrue(schedules.isEmpty());
        // ... (Add specific assertions based on your test data)
    }
    @Test
    void findByClassroom_ShouldReturnALlSchedulesByClassroom() {
        // Create some sample schedules with different days of the week and save them
        // ...

        List<Schedule> expected = List.of(
                createSchedule(tcStub, "001", "Monday", Time.valueOf("8:45:0"), Time.valueOf("10:20:0") ),
                createSchedule(tcStub, "001", "Wednesday", Time.valueOf("14:45:0"), Time.valueOf("16:30:0") ),
                createSchedule(tcStub, "002", "Monday", Time.valueOf("8:45:0"), Time.valueOf("10:20:0") ),
                createSchedule(tcStub, "P14", "Tuesday", Time.valueOf("10:30:0"), Time.valueOf("12:0:0") )
        );
        scheduleRepository.saveAll(expected);
        // Find schedules by a specific day of the week
        // Assert that the list contains the expected schedules
        List<Schedule> schedules = scheduleRepository.findByClassroom("001");
        assertFalse(schedules.isEmpty());
        assertEquals(expected.subList(0,2), schedules);

        schedules = scheduleRepository.findByClassroom("002");
        assertFalse(schedules.isEmpty());
        assertEquals(expected.subList(2,3), schedules);

        schedules = scheduleRepository.findByClassroom("P12");
        assertTrue(schedules.isEmpty());
        // ... (Add specific assertions based on your test data)
    }

    @Test
    void deleteScheduleById() {
        // Create a sample schedule and save it
        Schedule schedule = createSchedule(tcStub, "001", "Monday", Time.valueOf("8:45:0"), Time.valueOf("10:20:0") );
        // ... (Set the necessary fields)
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Delete the schedule
        scheduleRepository.deleteById(savedSchedule.getId());

        // Assert that the schedule is deleted
        Optional<Schedule> deletedSchedule = scheduleRepository.findById(savedSchedule.getId());
        assertTrue(deletedSchedule.isEmpty());
    }
}