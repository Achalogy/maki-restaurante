package com.maki.web.service;

import com.maki.web.entities.Client;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.exception.InvalidCredentialsException;

public interface ClienteService extends ServiceInterface<Client> {

  public Client registrarCliente(
          Client cliente
  ) throws EntityConstraintException;

  public Client registrarCliente(
          String nombre, String apellido, String correo, String contrasena, String telefono, String direccion
  ) throws EntityConstraintException;

  public Client verificarCredenciales(
      Client cliente
  ) throws InvalidCredentialsException, EntityNotFoundException;

  public Client verificarCredenciales(
          String correo, String contrasena
  ) throws  InvalidCredentialsException, EntityNotFoundException;
}
