package com.bc46.trabea.employee;

import com.bc46.trabea.error.exception.ConflictException;
import com.bc46.trabea.error.exception.ResourceNotFoundException;
import com.bc46.trabea.user.User;
import com.bc46.trabea.user.UserRepository;
import com.bc46.trabea.workschedule.WorkSchedule;
import com.bc46.trabea.workschedule.WorkScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final WorkScheduleRepository workScheduleRepository;
    private final UserRepository userRepository;

    @Override
    public void approveSchedule(Integer workScheduleId) {
        if (!workScheduleRepository.existsById(workScheduleId)) {
            throw new ResourceNotFoundException("Work Schedule With Id " + workScheduleId + " Is Not Found");
        }

        Employee manager = findEmployeeByToken();

        WorkSchedule workSchedule = workScheduleRepository.findByIdAndIsApprovedIsNull(workScheduleId)
                .orElseThrow(() -> new ConflictException("Work Schedule With Id " + workScheduleId + " Is Already Handled"));

        workSchedule.setManager(manager);
        workSchedule.setIsApproved(true);
        WorkSchedule approvedWorkSchedule = workScheduleRepository.save(workSchedule);
    }

    @Override
    public void disapproveSchedule(Integer id) {
        if (!workScheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Work Schedule With Id " + id + " Is Not Found");
        }

        Employee manager = findEmployeeByToken();

        WorkSchedule workSchedule = workScheduleRepository.findByIdAndIsApprovedIsNull(id)
                .orElseThrow(() -> new ConflictException("Work Schedule With Id " + id + " Is Already Handled"));

        workSchedule.setManager(manager);
        workSchedule.setIsApproved(false);
        WorkSchedule approvedWorkSchedule = workScheduleRepository.save(workSchedule);
    }

    @Override
    public Employee findEmployeeByToken() {
        User user = userRepository.findById(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ResourceNotFoundException("User With Work Email " + SecurityContextHolder.getContext().getAuthentication().getName() + " Is Not Found"));

        return employeeRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found"));
    }
}
