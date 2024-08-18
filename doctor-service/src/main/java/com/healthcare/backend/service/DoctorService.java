package com.healthcare.backend.service;

import com.healthcare.backend.dto.AddNewDoctorRequestDto;
import com.healthcare.backend.dto.GetDoctorResponseDto;
import com.healthcare.backend.dto.UpdateDoctorRequestDto;

import java.util.List;

public interface DoctorService {
    void createDoctor(AddNewDoctorRequestDto requestDto);

    void updateDoctor(UpdateDoctorRequestDto requestDto);

    GetDoctorResponseDto getById(Long id);

    GetDoctorResponseDto getDetail();

    List<GetDoctorResponseDto> getAll();
}
