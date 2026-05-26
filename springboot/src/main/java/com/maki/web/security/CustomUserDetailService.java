package com.maki.web.security;

import com.maki.web.entities.*;
import com.maki.web.repository.RoleRepository;
import com.maki.web.repository.UserEntityRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio central de autenticación. 1. Carga usuarios para Spring Security (loadUserByUsername) 2.
 * Convierte entidades de negocio a UserEntity para guardar en la tabla users
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired private UserEntityRepository userRepository;

    @Autowired private RoleRepository roleRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    // =====================================================================
    // SPRING SECURITY - carga usuario por username para validar el JWT
    // =====================================================================

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userDB =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () ->
                                        new UsernameNotFoundException(
                                                "Usuario no encontrado: " + username));

        return new User(
                userDB.getUsername(),
                userDB.getPassword(),
                mapRolesToAuthorities(userDB.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    // =====================================================================
    // MÉTODOS AUXILIARES - convierten entidades de negocio a UserEntity
    // Usados en los controllers al registrar un nuevo usuario
    // =====================================================================

    /**
     * Convierte un Client a UserEntity y lo guarda en la tabla users. El username del cliente es su
     * email. La contraseña se guarda encriptada. Los clientes no tienen contraseña propia, se les
     * asigna "123" por defecto.
     */
    public UserEntity clientToUserEntity(Client client) {
        // Busca o crea el rol CLIENT
        Role roleClient =
                roleRepository
                        .findByName("CLIENT")
                        .orElseGet(() -> roleRepository.save(new Role("CLIENT")));

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(client.getEmail());
        // Clientes se identifican solo con email (sin contraseña propia)
        userEntity.setPassword(passwordEncoder.encode("123"));
        userEntity.addRole(roleClient);

        return userRepository.save(userEntity);
    }

    /**
     * Convierte un Operator a UserEntity y lo guarda en la tabla users. El username del operador es
     * su username. La contraseña se guarda encriptada.
     */
    public UserEntity operatorToUserEntity(Operator operator) {
        Role roleOperator =
                roleRepository
                        .findByName("OPERATOR")
                        .orElseGet(() -> roleRepository.save(new Role("OPERATOR")));

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(operator.getUsername());
        userEntity.setPassword(passwordEncoder.encode(operator.getPassword()));
        userEntity.addRole(roleOperator);

        return userRepository.save(userEntity);
    }

    /**
     * Convierte un Administrator a UserEntity y lo guarda en la tabla users. El username del admin
     * es su username. La contraseña se guarda encriptada.
     */
    public UserEntity administratorToUserEntity(Administrator administrator) {
        Role roleAdmin =
                roleRepository
                        .findByName("ADMIN")
                        .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(administrator.getUsername());
        userEntity.setPassword(passwordEncoder.encode(administrator.getPassword()));
        userEntity.addRole(roleAdmin);

        return userRepository.save(userEntity);
    }
}
