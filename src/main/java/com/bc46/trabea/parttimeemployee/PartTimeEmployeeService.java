package com.bc46.trabea.parttimeemployee;

import com.bc46.trabea.parttimeemployee.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartTimeEmployeeService {
    Page<PartTimeEmployeeResponse> getPartTimeEmployees(String fullName, Pageable pageable);
    PartTimeEmployeeDetailResponse getPartTimeEmployeeById(Integer id);
    PartTimeEmployeeUpsertResponse registerPartTimeEmployee(PartTimeEmployeeInsertRequest request);
    PartTimeEmployeeUpsertResponse updatePartTimeEmployee(Integer id, PartTimeEmployeeUpdateRequest request);
    boolean existsByPersonalEmail(String email);
    boolean existsByPersonalPhoneNumber(String personalPhoneNumber);
    PartTimeEmployee findPartTimeEmployeeByToken();
}
