package com.maki.web.security;

import org.springframework.stereotype.Service;

import com.maki.web.repository.RoleRepository;
import com.maki.web.entities.Role;
import com.maki.web.entities.Client;
import com.maki.web.entities.UserEntity;
import com.maki.web.entities.Operator;
import com.maki.web.entities.Administrator;
import com.maki.web.repository.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CustomUserDetailService implements UserDetailsService {
  // UserEntity -> UserDetailService

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userDB = userRepository.findByUsername(username).orElseThrow(
      () -> new UsernameNotFoundException("User not found")
    );

    UserDetails userDetails = new User(userDB.getUsername(), userDB.getPassword(), asGrantedAuthorities(userDB.getRole()));

    return userDetails;
  }

  private Collection<GrantedAuthority> asGrantedAuthorities(Role rol) {
    return Arrays.asList(new SimpleGrantedAuthority(rol.getName()));
    // return Arrays.asList(rol).stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
  }

  public UserEntity ClientToUserEntity(Client client) {
    UserEntity user = new UserEntity();
    user.setUsername(client.getEmail());
    user.setPassword(passwordEncoder.encode(client.getPassword()));

    Role role = roleRepository.findByName("CLIENT").orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    user.setRole(role);

    return user;
  }

  public UserEntity AdministratorToUserEntity(Administrator admin) {
    UserEntity user = new UserEntity();
    user.setUsername(admin.getUsername());
    user.setPassword(passwordEncoder.encode(admin.getPassword()));

    Role role = roleRepository.findByName("ADMIN").get();
    user.setRole(role);

    return user;
  }

  public UserEntity OperatorToUserEntity(Operator operator) {
    UserEntity user = new UserEntity();
    user.setUsername(operator.getUsername());
    user.setPassword(passwordEncoder.encode(operator.getPassword()));

    Role role = roleRepository.findByName("OPERATOR").get();
    user.setRole(role);

    return user;
  }
}
