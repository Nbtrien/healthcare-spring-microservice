package com.healthcare.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddNewDoctorRequestDto implements Serializable {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String phoneNumber;
    private String specialty;
    private String medicalSchool;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate graduationDate;

}
