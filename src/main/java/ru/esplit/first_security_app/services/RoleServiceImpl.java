package ru.esplit.first_security_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.models.Role;
import ru.esplit.first_security_app.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final AdminService adminService;
    

    public RoleServiceImpl(RoleRepository roleRepository,
            AdminService adminService) {
        this.roleRepository = roleRepository;
        this.adminService = adminService;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public void takeBackTheRole(long id, String role_id) {
        Optional<Role> roleO = roleRepository.findById(role_id);
        Optional<Person> personO = adminService.getOnePerson(id);
        if (personO.isPresent() & roleO.isPresent()) {
            if (personO.get().getRoles().contains(roleO.get())) {
                personO.get().getRoles().remove(roleO.get());
            }
        }
    }

    @Transactional
    @Override
    public void giveTheRole(long id, String role_id) {
        Optional<Role> roleO = roleRepository.findById(role_id);
        Optional<Person> personO = adminService.getOnePerson(id);
        if (personO.isPresent() & roleO.isPresent()) {
            if (!personO.get().getRoles().contains(roleO.get())) {
                List<Role> lr = (List<Role>) personO.get().getRoles();
                lr.add(roleO.get());
            }
        }
    }

}
