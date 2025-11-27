package com.bc46.trabea.workschedule;

import com.bc46.trabea.employee.Employee;
import com.bc46.trabea.parttimeemployee.PartTimeEmployee;
import com.bc46.trabea.workshift.WorkShift;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "WorkSchedules")
public class WorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "WorkDate", nullable = false)
    private LocalDate workDate;

    @Column(name = "IsApproved")
    private Boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "ManagerId")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "PartTimeEmployeeId", nullable = false)
    private PartTimeEmployee partTimeEmployee;

    @ManyToOne
    @JoinColumn(name = "WorkShiftId", nullable = false)
    private WorkShift workShift;
}
