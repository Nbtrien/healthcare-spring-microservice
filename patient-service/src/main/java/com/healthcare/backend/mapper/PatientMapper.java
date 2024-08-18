package com.healthcare.backend.mapper;

import com.healthcare.backend.dto.AddNewPatientRequestDto;
import com.healthcare.backend.dto.GetPatientResponseDto;
import com.healthcare.backend.dto.UpdatePatientRequestDto;
import com.healthcare.backend.entity.Patient;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {
    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    Patient toEntity(AddNewPatientRequestDto requestDto);

    GetPatientResponseDto toPatientResponseDto(Patient patient);

    void updateEntity(@MappingTarget Patient entity, UpdatePatientRequestDto requestDto);
}
