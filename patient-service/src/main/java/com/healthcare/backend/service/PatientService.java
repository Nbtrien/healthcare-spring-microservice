package com.healthcare.backend.service;

import com.healthcare.backend.dto.AddNewPatientRequestDto;
import com.healthcare.backend.dto.GetPatientResponseDto;
import com.healthcare.backend.dto.UpdatePatientRequestDto;

import java.util.List;

public interface PatientService {
    void createPatient(AddNewPatientRequestDto requestDto);

    void updatePatient(UpdatePatientRequestDto requestDto);

    GetPatientResponseDto getById(Long id);

    GetPatientResponseDto getDetail();

    List<GetPatientResponseDto> getAll();
}
