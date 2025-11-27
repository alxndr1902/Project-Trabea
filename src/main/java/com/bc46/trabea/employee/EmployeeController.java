package com.bc46.trabea.employee;

import com.bc46.trabea.parttimeemployee.PartTimeEmployeeService;
import com.bc46.trabea.parttimeemployee.dto.*;
import com.bc46.trabea.workschedule.WorkScheduleService;
import com.bc46.trabea.workschedule.dto.WorkScheduleResponse;
import com.bc46.trabea.workschedule.dto.WorkScheduleReviewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final WorkScheduleService workScheduleService;
    private final EmployeeService employeeService;
    private final PartTimeEmployeeService partTimeEmployeeService;

    @GetMapping("managers/work-schedules")
    public ResponseEntity<Page<WorkScheduleResponse>> getWorkSchedules(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(workScheduleService.getWorkSchedules(pageable));
    }

    @GetMapping("managers/work-schedules/{id}")
    public ResponseEntity<WorkScheduleResponse> getWorkScheduleById(
            @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(workScheduleService.getWorkScheduleById(id));
    }

    @GetMapping("managers/work-schedules/review")
    public ResponseEntity<Page<WorkScheduleReviewResponse>> getWorkSchedules(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "workDate") String sort,
            //TODO
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer shiftId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        return ResponseEntity.status(HttpStatus.OK).body(workScheduleService.getWorkSchedulesReviews(name, shiftId, startDate, endDate, pageable));
    }

    @PatchMapping("managers/work-schedules/{id}/approve")
    public ResponseEntity<Void> approve(
            @PathVariable Integer id) {
        employeeService.approveSchedule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("managers/work-schedules/{id}/disapprove")
    public ResponseEntity<Void> dissaprove(
            @PathVariable Integer id) {
        employeeService.disapproveSchedule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("administrators/part-timers")
    public ResponseEntity<Page<PartTimeEmployeeResponse>> getPartTimeEmployees(
            @RequestParam(required = false) String fullName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "user.workEmail") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.status(HttpStatus.OK).body(partTimeEmployeeService.getPartTimeEmployees(fullName, pageable));
    }

    @GetMapping("administrators/part-timers/{id}")
    public ResponseEntity<PartTimeEmployeeDetailResponse> getPartTimeEmployee(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(partTimeEmployeeService.getPartTimeEmployeeById(id));
    }

    @PostMapping("administrators/part-timers")
    public ResponseEntity<PartTimeEmployeeUpsertResponse> registerPartTimeEmployee(@Valid @RequestBody PartTimeEmployeeInsertRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partTimeEmployeeService.registerPartTimeEmployee(request));
    }

    @PutMapping("administrators/part-timers/{id}")
    public ResponseEntity<PartTimeEmployeeUpsertResponse> updatePartTimeEmployee(@PathVariable Integer id, @Valid @RequestBody PartTimeEmployeeUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(partTimeEmployeeService.updatePartTimeEmployee(id, request));
    }
}
