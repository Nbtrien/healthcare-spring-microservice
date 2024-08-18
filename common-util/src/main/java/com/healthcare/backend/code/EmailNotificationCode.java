package com.healthcare.backend.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum EmailNotificationCode {
    CREATE_PATIENT_ACCOUNT(1, "CREATE_PATIENT_ACCOUNT"), CREATE_DOCTOR_ACCOUNT(2, "CREATE_DOCTOR_ACCOUNT");
    private int code;
    private String label;
}
