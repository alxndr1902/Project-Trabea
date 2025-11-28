package com.bc46.trabea.parttimeemployee;

import com.bc46.trabea.employee.Employee;
import com.bc46.trabea.error.exception.BadRequestException;
import com.bc46.trabea.error.exception.UnauthorizedException;
import com.bc46.trabea.parttimeemployee.dto.*;
import com.bc46.trabea.error.exception.ConflictException;
import com.bc46.trabea.error.exception.ResourceNotFoundException;
import com.bc46.trabea.role.Role;
import com.bc46.trabea.role.RoleName;
import com.bc46.trabea.role.RoleRepository;
import com.bc46.trabea.user.User;
import com.bc46.trabea.user.UserRepository;
import com.bc46.trabea.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PartTimeEmployeeServiceImpl implements PartTimeEmployeeService{
    private final PartTimeEmployeeRepository partTimeEmployeeRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PartTimeEmployeeMapper partTimeEmployeeMapper;
    private final UserService userService;

    @Override
    @Transactional
    public PartTimeEmployeeUpsertResponse registerPartTimeEmployee(PartTimeEmployeeInsertRequest request) {
        if (partTimeEmployeeRepository.existsByPersonalEmail(request.getPersonalEmail())) {
            throw new BadRequestException("Email is unavailable");
        }
        if (partTimeEmployeeRepository.existsByPersonalPhoneNumber(request.getPersonalPhoneNumber())) {
            throw new BadRequestException("Phone Number is unavailable");
        }

        User user = createUser(request);
        User savedUser = userRepository.save(user);

        PartTimeEmployee partTimeEmployee = createPartTimeEmployee(request, savedUser);
        PartTimeEmployee savedPartTimeEmployee = partTimeEmployeeRepository.save(partTimeEmployee);

        return partTimeEmployeeMapper.toPartTimeEmployeeUpsertResponse(savedPartTimeEmployee);
    }

    @Override
    public Page<PartTimeEmployeeResponse> getPartTimeEmployees(String fullName, Pageable pageable) {
        Page<PartTimeEmployee> partTimeEmployeesPage;

        if (fullName != null &&  !fullName.trim().isEmpty()) {
            partTimeEmployeesPage = partTimeEmployeeRepository.findAllByFullNameAndResignDateIsNull(fullName.trim(), pageable);
        } else {
            partTimeEmployeesPage = partTimeEmployeeRepository.findAllByResignDateIsNull(pageable);
        }
        return partTimeEmployeesPage.map(partTimeEmployeeMapper::toPartTimeEmployeeResponse);
    }

    @Override
    public PartTimeEmployeeDetailResponse getPartTimeEmployeeById(Integer id) {
        PartTimeEmployee partTimeEmployee = partTimeEmployeeRepository.findByIdAndResignDateIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Part Time Employee with Id " + id + " Is Not Found"));
        return  partTimeEmployeeMapper.toPartTimeEmployeeDetailResponse(partTimeEmployee);
    }


    @Override
    @Transactional
    public PartTimeEmployeeUpsertResponse updatePartTimeEmployee(Integer id, PartTimeEmployeeUpdateRequest request) {
        PartTimeEmployee partTimeEmployee = partTimeEmployeeRepository.findByIdAndResignDateIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Part Time Employee with Id " + id + " Is Not Found"));

        if (!partTimeEmployee.getPersonalEmail().equals(request.getPersonalEmail()) && partTimeEmployeeRepository.existsByPersonalEmail(request.getPersonalEmail())) {
            throw new ConflictException("Email is unavailable");
        }

        if (!partTimeEmployee.getPersonalPhoneNumber().equals(request.getPersonalPhoneNumber()) && partTimeEmployeeRepository.existsByPersonalPhoneNumber(request.getPersonalPhoneNumber())) {
            throw new ConflictException("Phone Number is unavailable");
        }

        partTimeEmployee = updatePartTimeEmployeeData(partTimeEmployee, request);
        PartTimeEmployee updatedPartTimeEmployee = partTimeEmployeeRepository.save(partTimeEmployee);

        return partTimeEmployeeMapper.toPartTimeEmployeeUpsertResponse(updatedPartTimeEmployee);
    }

    @Override
    public boolean existsByPersonalEmail(String email) {
        return !partTimeEmployeeRepository.existsByPersonalEmail(email);
    }

    @Override
    public boolean existsByPersonalPhoneNumber(String personalPhoneNumber) {
        return !partTimeEmployeeRepository.existsByPersonalPhoneNumber(personalPhoneNumber);
    }

    @Override
    public PartTimeEmployee findPartTimeEmployeeByToken() {
        User user = userService.findUserByToken();
        return partTimeEmployeeRepository.findByUserAndResignDateIsNull(user)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found"));
    }

    private User createUser(PartTimeEmployeeInsertRequest request) {
        User user = new User();
        user.setWorkEmail(request.getFirstName() + "." + request.getLastName() + "@trabea.co.id");
        user.setPassword(passwordEncoder.encode("Trabea123"));
        Role role = roleRepository.findByName(RoleName.PARTTIMER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user.addRole(role);
        return user;
    }

    private PartTimeEmployee createPartTimeEmployee(PartTimeEmployeeInsertRequest request, User savedUser) {
        PartTimeEmployee partTimeEmployee = partTimeEmployeeMapper.toPartTimeEmployee(request);
        partTimeEmployee.setUser(savedUser);
        partTimeEmployee.setJoinDate(LocalDateTime.now());
        return partTimeEmployee;
    }

    private PartTimeEmployee updatePartTimeEmployeeData(PartTimeEmployee partTimeEmployee, PartTimeEmployeeUpdateRequest request) {
        partTimeEmployee.setFirstName(request.getFirstName());
        partTimeEmployee.setLastName(request.getLastName());
        partTimeEmployee.setPersonalEmail(request.getPersonalEmail());
        partTimeEmployee.setPersonalPhoneNumber(request.getPersonalPhoneNumber());
        partTimeEmployee.setLastEducation(request.getLastEducation());
        partTimeEmployee.setOnGoingEducation(request.getOnGoingEducation());

        return partTimeEmployee;
    }

}
