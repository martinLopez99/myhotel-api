package com.myhotel.backendtest.repository.hr;

import com.myhotel.backendtest.entity.hr.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

/**
 * Repository Spring Data para Region.
 * @author Martin Lopez
 */
public interface RegionRepository extends JpaRepository<Region, BigDecimal> {
}
