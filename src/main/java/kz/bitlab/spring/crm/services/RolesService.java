package kz.bitlab.spring.crm.services;

import kz.bitlab.spring.crm.models.Roles;

import java.util.List;

public interface RolesService {

    List<Roles> getAllRoles();
    Roles getRolesById(Long id);
}
