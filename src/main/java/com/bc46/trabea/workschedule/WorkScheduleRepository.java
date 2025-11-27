package com.bc46.trabea.workschedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Integer>, JpaSpecificationExecutor<WorkSchedule> {
    Page<WorkSchedule> findAllByIsApprovedTrue(Pageable pageable);

    @Query("""
    SELECT ws
    FROM WorkSchedule ws
    WHERE ws.isApproved IS NULL
    AND 
    (:name IS NULL OR LOWER(CONCAT(ws.partTimeEmployee.firstName, ' ', ws.partTimeEmployee.lastName))
        LIKE LOWER(CONCAT('%', :name, '%')))
    AND (:shiftId IS NULL OR ws.workShift.id = :shiftId)
    AND (:startDate IS NULL or ws.workDate >= :startDate)
    AND (:endDate IS NULL OR ws.workDate <= :endDate)
""")
    Page<WorkSchedule> findAllWithFilters(
            @Param("name") String name,
            @Param("shiftId") Integer shiftId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);

    Optional<WorkSchedule> findByIdAndIsApprovedIsNull(Integer id);

    Optional<WorkSchedule> findByIdAndIsApprovedIsTrue(Integer id);

    @Query("""
        SELECT new com.bc46.trabea.workschedule.WorkScheduleCount (
                SUM(CASE WHEN ws.workDate = :workDate THEN 1 ELSE 0 END),
                SUM(CASE WHEN ws.workDate BETWEEN :startWeek AND :endWeek THEN 1 ELSE 0 END)
                )
                FROM WorkSchedule ws
                WHERE ws.partTimeEmployee.id = :partTimeEmployeeId
        """)
    WorkScheduleCount countWorkSchedule(
            @Param("partTimeEmployeeId") Integer partTimeEmployeeId,
            @Param("workDate") LocalDate workDate,
            @Param("startWeek") LocalDate startWeek,
            @Param("endWeek") LocalDate endWeek);

    @Query("""
            SELECT ws.workShift.id
            FROM WorkSchedule ws
            WHERE ws.partTimeEmployee.id = :partTimeEmployeeId AND ws.workDate = :workDate
        """)
    Integer findShiftIdsByPartTimeEmployeeIdAndWorkDate(
            @Param("partTimeEmployeeId") Integer partTimeEmployeeId,
            @Param("workDate") LocalDate workDate);
}
