package com.bc46.trabea.workschedule.dto;

import com.bc46.trabea.workshift.WorkShift;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkScheduleReviewResponse {
    private final Integer id;
    private final String fullName;
    private final LocalDate proposedDate;
    private final WorkShift workShift;
}
