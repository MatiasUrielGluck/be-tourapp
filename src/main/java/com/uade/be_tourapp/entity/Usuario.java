package com.uade.be_tourapp.entity;

import com.uade.be_tourapp.enums.AuthStrategiesEnum;
import com.uade.be_tourapp.enums.GenerosEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rol", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("USUARIO")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "proveedor")
    @Enumerated(EnumType.STRING)
    private AuthStrategiesEnum proveedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private GenerosEnum genero;

    @Column(name = "dni", unique = true)
    private Integer dni;

    @Column(name = "num_telefono")
    private String numTelefono;

    @Column(name = "foto")
    private String foto;

    @Column(name = "kyc_completed", nullable = false)
    private Boolean kycCompleted;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
