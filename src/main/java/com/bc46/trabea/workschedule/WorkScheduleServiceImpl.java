package com.bc46.trabea.workschedule;

import com.bc46.trabea.error.exception.BadRequestException;
import com.bc46.trabea.error.exception.ResourceNotFoundException;
import com.bc46.trabea.parttimeemployee.PartTimeEmployee;
import com.bc46.trabea.parttimeemployee.PartTimeEmployeeService;
import com.bc46.trabea.workschedule.dto.WorkScheduleRequest;
import com.bc46.trabea.workschedule.dto.WorkScheduleResponse;
import com.bc46.trabea.workschedule.dto.WorkScheduleReviewResponse;
import com.bc46.trabea.workshift.WorkShift;
import com.bc46.trabea.workshift.WorkShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WorkScheduleServiceImpl implements WorkScheduleService {
    private final WorkScheduleRepository workScheduleRepository;
    private final WorkScheduleMapper workScheduleMapper;
    private final WorkShiftRepository workShiftRepository;
    private final PartTimeEmployeeService partTimeEmployeeService;

    @Override
    public Page<WorkScheduleResponse> getWorkSchedules(Pageable pageable) {
        Page<WorkSchedule> workSchedulePage = workScheduleRepository.findAllByIsApprovedTrue(pageable);
        return workSchedulePage.map(workScheduleMapper::toWorkScheduleResponse);
    }

    @Override
    public WorkScheduleResponse     getWorkScheduleById(Integer id) {
        WorkSchedule workSchedule = workScheduleRepository.findByIdAndIsApprovedIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Work Schedule With Id " + id + " Is Not Found"));

        return workScheduleMapper.toWorkScheduleResponse(workSchedule);
    }

    @Override
    public WorkScheduleResponse createWorkSchedule(WorkScheduleRequest request) {
        PartTimeEmployee partTimeEmployee = partTimeEmployeeService.findPartTimeEmployeeByToken();
        Integer partTimeEmployeeId = partTimeEmployee.getId();

        LocalDate validatedWorkDate = validateWorkDate(partTimeEmployeeId, request);
        WorkShift validatedWorkShift = validateWorkShift(partTimeEmployeeId, request);

        WorkSchedule workSchedule = createWorkSchedule(partTimeEmployee, validatedWorkDate, validatedWorkShift);
        WorkSchedule savedWorkSchedule = workScheduleRepository.save(workSchedule);
        return workScheduleMapper.toWorkScheduleResponse(savedWorkSchedule);
    }

    @Override
    public Page<WorkScheduleReviewResponse> getWorkSchedulesReviews(String name, Integer shiftId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<WorkSchedule> workSchedulePage = workScheduleRepository.findAllWithFilters(name, shiftId, startDate, endDate, pageable);
        return workSchedulePage.map(workScheduleMapper::toWorkScheduleReviewResponse);
    }

    private LocalDate validateWorkDate(Integer partTimeEmployeeId, WorkScheduleRequest request) {
        LocalDate startWeek = request.getWorkDate().with(DayOfWeek.MONDAY);
        LocalDate endWeek = request.getWorkDate().with(DayOfWeek.FRIDAY);

        WorkScheduleCount workScheduleCount = workScheduleRepository.countWorkSchedule(partTimeEmployeeId, request.getWorkDate(),  startWeek, endWeek);

        if (workScheduleCount.getDailyCount() >= 2) {
            throw new BadRequestException("Maximum 2 Work Schedules Allowed In One Day");
        }

        if (workScheduleCount.getWeeklyCount() >= 5) {
            throw new BadRequestException("Maximum 5 Work Schedules Allowed In One Week");
        }

        return request.getWorkDate();
    }

    private WorkShift validateWorkShift(Integer partTimeEmployeeId, WorkScheduleRequest request) {
        WorkShift workShift = workShiftRepository.findById(request.getShiftId())
                .orElseThrow(() -> new ResourceNotFoundException("Work Shift With Id " + request.getShiftId() + " Is Not Found"));

        Integer existingShiftId = workScheduleRepository.findShiftIdsByPartTimeEmployeeIdAndWorkDate(partTimeEmployeeId, request.getWorkDate());

        if (existingShiftId != null) {
            if (Math.abs(existingShiftId - request.getShiftId()) != 1) {
                throw new BadRequestException("New Work Schedule With Shift Id " + request.getShiftId() +
                        " Must Be Adjacent To Existing Work Schedule With Shift Id " + existingShiftId);
            }
        }

        return workShift;
    }

    private WorkSchedule createWorkSchedule(PartTimeEmployee partTimeEmployee, LocalDate validatedWorkDate, WorkShift validatedWorkShift) {
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setPartTimeEmployee(partTimeEmployee);
        workSchedule.setWorkDate(validatedWorkDate);
        workSchedule.setWorkShift(validatedWorkShift);
        return workSchedule;
    }
}
