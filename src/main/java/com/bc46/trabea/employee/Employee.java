package com.bc46.trabea.employee;

import com.bc46.trabea.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "FirstName", nullable = false, length = 25)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 50)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "WorkEmail", nullable = false)
    private User user;
}
