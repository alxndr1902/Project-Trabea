package com.bc46.trabea.parttimeemployee;

import com.bc46.trabea.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PartTimeEmployees")
public class PartTimeEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "FirstName", length = 25, nullable = false)
    private String firstName;

    @Column(name = "LastName", length = 50, nullable = false)
    private String lastName;

    @Column(name = "PersonalEmail", length = 100, nullable = false, unique = true)
    private String personalEmail;

    @Column(name = "PersonalPhoneNumber", length = 20, nullable = false, unique = true)
    private String personalPhoneNumber;

    @Column(name = "LastEducation", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Education lastEducation;

    @Column(name = "OnGoingEducation", length = 50)
    @Enumerated(EnumType.STRING)
    private Education onGoingEducation;

    @Column(name = "JoinDate", nullable = false)
    private LocalDateTime joinDate = LocalDateTime.now();

    @Column(name = "ResignDate")
    private LocalDateTime resignDate;

    @OneToOne
    @JoinColumn(name = "WorkEmail", nullable = false)
    private User user;
}
