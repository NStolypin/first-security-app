package ru.esplit.first_security_app.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Role")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToMany(mappedBy = "roles")
    private List<Person> persons;

    @Override
    public String getAuthority() {
        return this.id;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Role_Operation", uniqueConstraints = @UniqueConstraint(columnNames = { "role_id",
            "operation_id" }), joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "operation_id", referencedColumnName = "id"))
    private List<Operation> allowedOperations;

    public Collection<? extends GrantedAuthority> getAllowedOperations() {
        return allowedOperations;
    }
}
