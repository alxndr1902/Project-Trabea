package com.bc46.trabea.parttimeemployee;

import com.bc46.trabea.workschedule.WorkScheduleService;
import com.bc46.trabea.workschedule.dto.WorkScheduleRequest;
import com.bc46.trabea.workschedule.dto.WorkScheduleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employees/part-timers")
@RequiredArgsConstructor
public class PartTimeEmployeeController {
    private final WorkScheduleService workScheduleService;

    @GetMapping("work-schedules")
    public ResponseEntity<Page<WorkScheduleResponse>> getWorkSchedules(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.status(HttpStatus.OK).body(workScheduleService.getWorkSchedules(pageable));
    }

    @GetMapping("work-schedules/{id}")
    public ResponseEntity<WorkScheduleResponse> getWorkScheduleById(
            @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(workScheduleService.getWorkScheduleById(id));
    }

    @PostMapping("work-schedules")
    public ResponseEntity<WorkScheduleResponse> createWorkSchedule(
            @Valid @RequestBody WorkScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workScheduleService.createWorkSchedule(request));
    }
}
