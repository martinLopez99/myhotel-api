package com.myhotel.backendtest.repository.hr;

import com.myhotel.backendtest.entity.hr.JobHistory;
import com.myhotel.backendtest.entity.hr.JobHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Spring Data para JobHistory.
 * @author Martin Lopez
 */
public interface JobHistoryRepository extends JpaRepository<JobHistory, JobHistoryId> {
}
