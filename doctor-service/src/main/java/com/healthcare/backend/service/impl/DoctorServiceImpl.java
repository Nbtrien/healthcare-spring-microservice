package com.healthcare.backend.service.impl;

import com.healthcare.backend.code.EmailNotificationCode;
import com.healthcare.backend.dto.AddNewDoctorRequestDto;
import com.healthcare.backend.dto.GetDoctorResponseDto;
import com.healthcare.backend.dto.UpdateDoctorRequestDto;
import com.healthcare.backend.entity.Doctor;
import com.healthcare.backend.event.EmailNotification;
import com.healthcare.backend.event.EmailNotificationProducer;
import com.healthcare.backend.exception.ResourceNotFoundException;
import com.healthcare.backend.mapper.DoctorMapper;
import com.healthcare.backend.repository.DoctorRepository;
import com.healthcare.backend.service.DoctorService;
import com.healthcare.backend.service.KeycloakUserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final KeycloakUserService keycloakUserService;
    private final DoctorRepository doctorRepository;
    private final EmailNotificationProducer emailNotificationProducer;

    @Override
    public void createDoctor(AddNewDoctorRequestDto requestDto) {
        UserRepresentation user = new UserRepresentation();

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(requestDto.getPassword());
        credential.setTemporary(false);

        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setCredentials(Collections.singletonList(credential));

        String keycloakUserId = keycloakUserService.createUser(user);

        Doctor doctor = DoctorMapper.INSTANCE.toEntity(requestDto);
        doctor.setKeycloakUserId(keycloakUserId);
        doctorRepository.save(doctor);
        sendMailNotification(requestDto);
    }

    @Override
    public void updateDoctor(UpdateDoctorRequestDto requestDto) {
        Doctor doctor = getCurrentUser();
        if (doctor == null)
            throw new ResourceNotFoundException("Doctor not found!");
        DoctorMapper.INSTANCE.updateEntity(doctor, requestDto);
        doctorRepository.save(doctor);
    }

    @Override
    public GetDoctorResponseDto getById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found!"));
        return DoctorMapper.INSTANCE.toDoctorResponseDto(doctor);
    }

    @Override
    public GetDoctorResponseDto getDetail() {
        Doctor doctor = getCurrentUser();
        if (doctor == null)
            throw new ResourceNotFoundException("Doctor not found!");
        return DoctorMapper.INSTANCE.toDoctorResponseDto(doctor);
    }

    @Override
    public List<GetDoctorResponseDto> getAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(DoctorMapper.INSTANCE::toDoctorResponseDto).collect(Collectors.toList());
    }

    private void sendMailNotification(AddNewDoctorRequestDto requestDto) {
        EmailNotification emailNotification = new EmailNotification();
        List<String> emails = new ArrayList<>();
        emails.add(requestDto.getEmail());
        emailNotification.setEmails(emails);

        HashMap<String, String> data = new HashMap<>();
        data.put("username", requestDto.getUsername());
        data.put("firstName", requestDto.getFirstName());
        data.put("lastName", requestDto.getLastName());
        data.put("password", requestDto.getPassword());
        emailNotification.setData(data);
        emailNotification.setType(EmailNotificationCode.CREATE_DOCTOR_ACCOUNT.getLabel());
        emailNotificationProducer.sendEmailNotificationMessage(emailNotification);
    }

    private Doctor getCurrentUser() {
        String keycloakUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return doctorRepository.findByKeycloakUserId(keycloakUserId).orElse(null);
    }
}
