package com.practice.practiceProject.entities;

import com.practice.practiceProject.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email_id")
    @Email(message = "Email Id is invalid")
    @NotBlank(message = "Email Id can't be blank")
    @NotNull(message = "Value can't be null")
    private String emailId;

    @Column(name = "first_name")
    @NotBlank(message = "First name can't be blank")
    @NotNull(message = "Value can't be null")
    @Size(min = 3, message = "First Name can't be less than 3 letters")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name can't be blank")
    private String lastName;

    @Column(name = "date_of_birth")
    @NotBlank(message = "Date of birth can't be blank")
    private String dateOfBirth;

    @Column(name = "password")
    @NotBlank(message = "Password can't be blank")
    private String password;

//    @Column(name = "is_active")
//    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    @Column(name = "roles")
    private Set<String> roles;
}
