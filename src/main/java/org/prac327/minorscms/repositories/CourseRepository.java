package org.prac327.minorscms.repositories;

import org.prac327.minorscms.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by ytati
 * on 22.05.2024.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);
    List<Course> findCourseByCompanyId(Long company);
    Integer countCoursesByCompanyId(Long company);
}
