package com.bc46.trabea.parttimeemployee;

import com.bc46.trabea.parttimeemployee.dto.*;
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
@RequestMapping("part-timers")
@RequiredArgsConstructor
public class PartTimeEmployeeController {
    private final PartTimeEmployeeService partTimeEmployeeService;

    @GetMapping
    public ResponseEntity<Page<PartTimeEmployeeResponse>> getPartTimeEmployees(
            @RequestParam(required = false) String fullName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "user.workEmail") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.status(HttpStatus.OK).body(partTimeEmployeeService.getPartTimeEmployees(fullName, pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<PartTimeEmployeeDetailResponse> getPartTimeEmployee(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(partTimeEmployeeService.getPartTimeEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<PartTimeEmployeeUpsertResponse> registerPartTimeEmployee(@Valid @RequestBody PartTimeEmployeeInsertRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partTimeEmployeeService.registerPartTimeEmployee(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<PartTimeEmployeeUpsertResponse> updatePartTimeEmployee(@PathVariable Integer id, @Valid @RequestBody PartTimeEmployeeUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(partTimeEmployeeService.updatePartTimeEmployee(id, request));
    }
}
