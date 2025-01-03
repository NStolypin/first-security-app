package ru.esplit.first_security_app.services;

import java.util.List;

import ru.esplit.first_security_app.models.Role;

public interface RoleService {

    List<Role> getAllRoles();

    void takeBackTheRole(long id, String role_id);

    void giveTheRole(long id, String role_id);
}
