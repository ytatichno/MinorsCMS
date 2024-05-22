package org.prac327.minorscms.repositories;

import org.prac327.minorscms.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by ytati
 * on 22.05.2024.
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByShortname(String string);
}
