package com.bc46.trabea.workschedule;

import com.bc46.trabea.employee.EmployeeService;
import com.bc46.trabea.workschedule.dto.WorkScheduleRequest;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("work-schedules")
@RequiredArgsConstructor
public class WorkScheduleController {
    private final WorkScheduleService workScheduleService;
    private final EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<Page<WorkScheduleResponse>> getWorkSchedules(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.status(HttpStatus.OK).body(workScheduleService.getWorkSchedules(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<WorkScheduleResponse> getWorkScheduleById(
            @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(workScheduleService.getWorkScheduleById(id));
    }

    @PostMapping
    public ResponseEntity<WorkScheduleResponse> createWorkSchedule(
            @Valid @RequestBody WorkScheduleRequest request) {
        System.out.println("Masuk controller");
        return ResponseEntity.status(HttpStatus.CREATED).body(workScheduleService.createWorkSchedule(request));
    }

    @GetMapping("review")
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

    @PatchMapping("{id}/approve")
    public ResponseEntity<Void> approve(
            @PathVariable Integer id) {
        employeeService.approveSchedule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("{id}/disapprove")
    public ResponseEntity<Void> dissaprove(
            @PathVariable Integer id) {
        employeeService.disapproveSchedule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
