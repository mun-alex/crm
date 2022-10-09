package kz.bitlab.spring.crm.services.impl;

import kz.bitlab.spring.crm.models.Roles;
import kz.bitlab.spring.crm.repository.RolesRepository;
import kz.bitlab.spring.crm.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public Roles getRolesById(Long id) {
        return rolesRepository.findById(id).orElseThrow();
    }
}
