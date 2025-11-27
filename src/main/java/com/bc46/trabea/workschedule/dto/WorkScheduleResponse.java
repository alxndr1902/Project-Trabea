package com.bc46.trabea.workschedule.dto;

import com.bc46.trabea.parttimeemployee.dto.PartTimeEmployeeSummaryResponse;
import com.bc46.trabea.workshift.WorkShift;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkScheduleResponse {
    private Integer workScheduleId;
    private final LocalDate startDate;
    private final PartTimeEmployeeSummaryResponse partTimeEmployee;
    private final WorkShift workShift;
}
