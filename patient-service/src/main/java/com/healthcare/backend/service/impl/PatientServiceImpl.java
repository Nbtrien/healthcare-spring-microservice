package com.healthcare.backend.service.impl;

import com.healthcare.backend.code.EmailNotificationCode;
import com.healthcare.backend.dto.AddNewPatientRequestDto;
import com.healthcare.backend.dto.GetPatientResponseDto;
import com.healthcare.backend.dto.UpdatePatientRequestDto;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.event.EmailNotification;
import com.healthcare.backend.event.EmailNotificationProducer;
import com.healthcare.backend.exception.ResourceNotFoundException;
import com.healthcare.backend.mapper.PatientMapper;
import com.healthcare.backend.repository.PatientRepository;
import com.healthcare.backend.service.KeycloakUserService;
import com.healthcare.backend.service.PatientService;
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
public class PatientServiceImpl implements PatientService {
    private final KeycloakUserService keycloakUserService;
    private final PatientRepository patientRepository;
    private final EmailNotificationProducer emailNotificationProducer;

    @Override
    public void createPatient(AddNewPatientRequestDto requestDto) {
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

        Patient patient = PatientMapper.INSTANCE.toEntity(requestDto);
        patient.setKeycloakUserId(keycloakUserId);
        patientRepository.save(patient);
        sendMailNotification(requestDto);
    }

    @Override
    public void updatePatient(UpdatePatientRequestDto requestDto) {
        Patient patient = getCurrentUser();
        if (patient == null)
            throw new ResourceNotFoundException("Patient not found!");
        PatientMapper.INSTANCE.updateEntity(patient, requestDto);
        patientRepository.save(patient);
    }

    @Override
    public GetPatientResponseDto getById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found!"));
        return PatientMapper.INSTANCE.toPatientResponseDto(patient);
    }

    @Override
    public GetPatientResponseDto getDetail() {
        Patient patient = getCurrentUser();
        if (patient == null)
            throw new ResourceNotFoundException("Patient not found!");
        return PatientMapper.INSTANCE.toPatientResponseDto(patient);
    }

    @Override
    public List<GetPatientResponseDto> getAll() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper.INSTANCE::toPatientResponseDto).collect(Collectors.toList());
    }

    private void sendMailNotification(AddNewPatientRequestDto requestDto) {
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
        emailNotification.setType(EmailNotificationCode.CREATE_PATIENT_ACCOUNT.getLabel());
        emailNotificationProducer.sendEmailNotificationMessage(emailNotification);
    }

    private Patient getCurrentUser() {
        String keycloakUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return patientRepository.findByKeycloakUserId(keycloakUserId).orElse(null);
    }
}
