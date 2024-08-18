package com.healthcare.backend.controller;

import com.healthcare.backend.dto.AddNewDoctorRequestDto;
import com.healthcare.backend.dto.UpdateDoctorRequestDto;
import com.healthcare.backend.service.DoctorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createDoctor(@RequestBody AddNewDoctorRequestDto requestDto) {
        doctorService.createDoctor(requestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(doctorService.getAll());
    }

    @PutMapping
    @PreAuthorize("hasRole('doctor')")
    public ResponseEntity<?> updateDoctor(@RequestBody UpdateDoctorRequestDto requestDto) {
        doctorService.updateDoctor(requestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(doctorService.getById(id));
    }

    @GetMapping("/profiles")
    @PreAuthorize("hasRole('doctor')")
    public ResponseEntity<?> getDetail() {
        return ResponseEntity.ok(doctorService.getDetail());
    }

    @GetMapping("/{id}/status")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> checkStatus(@PathVariable("id") Long id) {
        System.out.println(id);
        if (id == 1)
            return ResponseEntity.ok(true);
        return ResponseEntity.ok(false);
    }


}
