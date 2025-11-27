package com.bc46.trabea.workschedule.dto;

import com.bc46.trabea.workschedule.validator.NextWeekWorkDate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Data
public class WorkScheduleRequest {
    @NotNull(message = "Work Date Cannot be Null")
    @NextWeekWorkDate(message = "Chosen Date Must be Work Date of the Next Week")
    private LocalDate workDate;

    @NotNull(message = "Shift Id Cannot be Null")
    @Range(min = 1, max = 4, message = "Shift Id Must be Between 1 and 4 Only")
    private Integer shiftId;
}
