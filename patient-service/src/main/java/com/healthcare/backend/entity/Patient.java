package com.healthcare.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "PATIENT")
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "KEYCLOAK_USER_ID", nullable = false, unique = true)
    private String keycloakUserId;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
}
