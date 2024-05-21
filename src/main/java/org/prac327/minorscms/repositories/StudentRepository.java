package org.prac327.minorscms.repositories;

import org.prac327.minorscms.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ytati
 * on 22.05.2024.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}
