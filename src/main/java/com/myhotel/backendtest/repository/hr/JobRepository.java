package com.myhotel.backendtest.repository.hr;

import com.myhotel.backendtest.entity.hr.Job;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Spring Data para Job.
 * @author Martin Lopez
 */
public interface JobRepository extends JpaRepository<Job, String> {
}
