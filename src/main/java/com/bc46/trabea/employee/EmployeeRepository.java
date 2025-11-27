package com.bc46.trabea.employee;

import com.bc46.trabea.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUser(User user);
}
