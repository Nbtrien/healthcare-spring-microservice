package com.healthcare.backend.controller;

import com.healthcare.backend.client.DoctorClient;
import com.healthcare.backend.dto.AddNewPatientRequestDto;
import com.healthcare.backend.dto.UpdatePatientRequestDto;
import com.healthcare.backend.service.PatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@SecurityRequirement(name = "Keycloak")
public class PatientController {
    private final DoctorClient doctorClient;
    private final PatientService patientService;
    private final HttpServletRequest request;

    @GetMapping("/abc/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        boolean doctorStatus = doctorClient.checkStatus(id, request.getHeader("Authorization"));
        return ResponseEntity.ok(doctorStatus);
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createPatient(@RequestBody AddNewPatientRequestDto requestDto) {
        patientService.createPatient(requestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(patientService.getAll());
    }

    @PutMapping
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<?> updateDoctor(@RequestBody UpdatePatientRequestDto requestDto) {
        patientService.updatePatient(requestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    @GetMapping("/profiles")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<?> getDetail() {
        return ResponseEntity.ok(patientService.getDetail());
    }
}
