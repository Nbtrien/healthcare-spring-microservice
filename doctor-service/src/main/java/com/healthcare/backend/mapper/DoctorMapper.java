package com.healthcare.backend.mapper;

import com.healthcare.backend.dto.AddNewDoctorRequestDto;
import com.healthcare.backend.dto.GetDoctorResponseDto;
import com.healthcare.backend.dto.UpdateDoctorRequestDto;
import com.healthcare.backend.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DoctorMapper {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    Doctor toEntity(AddNewDoctorRequestDto requestDto);

    GetDoctorResponseDto toDoctorResponseDto(Doctor entity);

    void updateEntity(@MappingTarget Doctor entity, UpdateDoctorRequestDto requestDto);
}
