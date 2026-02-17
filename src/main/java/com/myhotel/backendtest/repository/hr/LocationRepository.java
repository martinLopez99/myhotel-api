package com.myhotel.backendtest.repository.hr;

import com.myhotel.backendtest.entity.hr.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

/**
 * Repository Spring Data para Location.
 * @author Martin Lopez
 */
public interface LocationRepository extends JpaRepository<Location, BigDecimal> {
}
