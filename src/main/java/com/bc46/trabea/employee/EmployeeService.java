package com.bc46.trabea.employee;

public interface EmployeeService {
    void approveSchedule(Integer workScheduleId);
    void disapproveSchedule(Integer workScheduleId);
    Employee findEmployeeByToken();
}
