package org.prac327.minorscms.repositories;

import org.junit.jupiter.api.Test;
import org.prac327.minorscms.models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by ytati
 * on 22.05.2024.
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CompanyRepositoryTest {
    @Autowired
    CompanyRepository companyRepository;
    @Test
    public void CompanyRepository_Save_ReturnSavedMessage(){
        Company company = new Company();
        company.setName("Абоба");
        company.setShortname("Аб");
        company.setDescription("descr");
        company.setAddress("Лучший город Земли");

        Company savedCompany = companyRepository.save(company);

        assertEquals(company, savedCompany);
    }

    @Test
    public void findAll_ShouldReturnAllCompanies() {
        List<Company> expected = List.of(
                new Company("Test Company0", "TC0", "Test Description", "Test Address"),
                new Company("Test Company1", "TC1", "Test Description", "Test Address"),
                new Company("Test Company2", "TC2", "Test Description", "Test Address"),
                new Company("Test Company3", "TC3", "Test Description", "Test Address")
        );
        companyRepository.saveAll(expected);
        // When - Perform the action
        List<Company> companies = companyRepository.findAll();

        // Then - Verify the result
        // Assert that the list is not empty and contains the expected companies
        assertFalse(companies.isEmpty());
        // ... add more assertions based on your specific data
        assertEquals(expected, companies);
    }

    @Test
    public void findById_ShouldReturnCorrectCompany() {
        // Given - Setup test data (you might need to add some companies in a @BeforeEach method)
        Company existingCompany = companyRepository.save(new Company("Test Company", "TC", "Test Description", "Test Address"));
        Long existingCompanyId = existingCompany.getId();

        // When - Perform the action
        Optional<Company> foundCompany = companyRepository.findById(existingCompanyId);

        // Then - Verify the result
        assertTrue(foundCompany.isPresent());
        assertEquals(existingCompany, foundCompany.get());
    }

    @Test
    public void findById_ShouldReturnEmptyOptionalWhenNotFound() {
        // Given - Setup test data (you might need to add some companies in a @BeforeEach method)
        // ...

        // When - Perform the action
        Optional<Company> foundCompany = companyRepository.findById(100L); // Assume 100L is an invalid ID

        // Then - Verify the result
        assertFalse(foundCompany.isPresent());
    }

    @Test
    public void findByShortname_ShouldReturnCorrectCompany() {
        // Given - Setup test data (you might need to add some companies in a @BeforeEach method)
        Company existingCompany = companyRepository.save(new Company("Test Company", "TC", "Test Description", "Test Address"));
        String shortName = existingCompany.getShortname();

        // When - Perform the action
        Optional<Company> foundCompany = companyRepository.findByShortname(shortName);

        // Then - Verify the result
        assertTrue(foundCompany.isPresent());
        assertEquals(existingCompany, foundCompany.get());
    }

    @Test
    public void deleteById_ShouldDeleteCorrectCompany() {
        // Given - Setup test data (you might need to add some companies in a @BeforeEach method)
        Company existingCompany = companyRepository.save(new Company("Test Company", "TC", "Test Description", "Test Address"));
        Long existingCompanyId = existingCompany.getId();

        // When - Perform the action
        companyRepository.deleteById(existingCompanyId);

        // Then - Verify the result
        Optional<Company> deletedCompany = companyRepository.findById(existingCompanyId);
        assertFalse(deletedCompany.isPresent());
    }
}