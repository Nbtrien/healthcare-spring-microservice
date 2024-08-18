package com.healthcare.backend.service;

import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakUserService {
    String createUser(UserRepresentation user);
}
