package com.bc46.trabea.parttimeemployee;

import com.bc46.trabea.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PartTimeEmployeeRepository extends JpaRepository<PartTimeEmployee, Integer> {
    Page<PartTimeEmployee> findAllByResignDateIsNull(Pageable pageable);
    @Query("""
            SELECT pte 
            FROM PartTimeEmployee pte
            WHERE pte.resignDate IS NULL
            AND (:fullName IS NULL OR LOWER(CONCAT(pte.firstName, ' ', pte.lastName))
            LIKE LOWER(CONCAT('%', :fullName, '%')))
            """)
    Page<PartTimeEmployee> findAllByFullNameAndResignDateIsNull(@Param("fullName") String fullName, Pageable pageable);
    Optional<PartTimeEmployee> findByIdAndResignDateIsNull(Integer id);

    Boolean existsByPersonalEmail(String personalEmail);
    Boolean existsByPersonalPhoneNumber(String phoneNumber);
    Optional<PartTimeEmployee> findByUserAndResignDateIsNull(User user);
}
