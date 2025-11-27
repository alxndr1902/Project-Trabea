package com.bc46.trabea.workschedule;

import com.bc46.trabea.workschedule.dto.WorkScheduleRequest;
import com.bc46.trabea.workschedule.dto.WorkScheduleResponse;
import com.bc46.trabea.workschedule.dto.WorkScheduleReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface WorkScheduleService {
    Page<WorkScheduleResponse> getWorkSchedules(Pageable pageable);

    Page<WorkScheduleReviewResponse> getWorkSchedulesReviews(String name, Integer shiftId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    WorkScheduleResponse getWorkScheduleById(Integer id);

    WorkScheduleResponse createWorkSchedule(WorkScheduleRequest request);

}
