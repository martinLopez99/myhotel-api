package com.myhotel.backendtest.repository.hr;

import com.myhotel.backendtest.entity.hr.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Spring Data para Country.
 * @author Martin Lopez
 */
public interface CountryRepository extends JpaRepository<Country, String> {
}
