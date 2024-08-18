package com.healthcare.backend.service.impl;

import com.healthcare.backend.service.KeycloakUserService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class KeycloakUserServiceImpl implements KeycloakUserService {
    @Value("${keycloak.realm}")
    public String realm;
    @Value("${keycloak.resource}")
    public String clientId;
    @Value("${keycloak.role.user}")
    public String keycloakUserRole;
    @Value("${keycloak.role.doctor}")
    public String keycloakDoctorRole;
    private final Keycloak keycloak;

    @Override
    public String createUser(UserRepresentation user) {
        UsersResource usersResource = keycloak.realm(realm).users();
        user.setEnabled(true);
        user.setEmailVerified(false);

        try (Response response = usersResource.create(user)) {
            if (response.getStatus() == 201) {
                UserRepresentation createdUser = usersResource.search(user.getUsername()).get(0);
                String keycloakClientId = keycloak.realm(realm).clients().findByClientId(clientId).get(0).getId();
                RoleRepresentation userRole = keycloak.realm(realm).clients().get(keycloakClientId).roles()
                        .get(keycloakUserRole).toRepresentation();

                RoleRepresentation patientRole = keycloak.realm(realm).clients().get(keycloakClientId).roles()
                        .get(keycloakDoctorRole).toRepresentation();

                List<RoleRepresentation> keycloakRoles = new ArrayList<>();
                keycloakRoles.add(userRole);
                keycloakRoles.add(patientRole);
                usersResource.get(createdUser.getId()).roles().clientLevel(keycloakClientId).add(keycloakRoles);
                log.info("Created user: " + user.getUsername() + ", id: " + user.getId() + "!");
                return createdUser.getId();
            } else {
                throw new RuntimeException(response.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRepresentation getById(String userId) {
        return null;
    }
}
