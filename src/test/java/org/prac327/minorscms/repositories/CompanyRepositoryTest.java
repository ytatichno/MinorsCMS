package org.prac327.minorscms.repositories;

import org.junit.jupiter.api.Test;
import org.prac327.minorscms.models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        company.setShort_name("Аб");
        company.setDescription("descr");
        company.setAddress("Лучший город Земли");

        Company savedCompany = companyRepository.save(company);

        assertEquals(company, savedCompany);
    }
}