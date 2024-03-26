package com.practice.practiceProject.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInputDto {

    @Size(min = 3, message = "First Name can't be less than 3 letters")
    @NotBlank(message = "First name can't be blank")
    @NotNull(message = "Value can't be null")
    private String firstName;

    @NotBlank(message = "Last name can't be blank")
    private String lastName;

    @NotBlank(message = "Date of birth can't be blank")
    private String dateOfBirth;

}
