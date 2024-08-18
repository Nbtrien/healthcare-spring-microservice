package com.healthcare.backend.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;

public interface DoctorClient {

    @GetExchange("/api/doctors/{id}/status")
    boolean checkStatus(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

}