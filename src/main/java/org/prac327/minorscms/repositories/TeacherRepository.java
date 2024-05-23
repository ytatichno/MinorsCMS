package org.prac327.minorscms.repositories;

import org.prac327.minorscms.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ytati
 * on 22.05.2024.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByLastname(String lastname);

    List<Teacher> findByCompanyShortname(String companyShortName);
}
