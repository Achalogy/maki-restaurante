package com.maki.web.service;

import com.maki.web.entities.Cliente;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.exception.InvalidCredentialsException;

public interface ClienteService extends ServiceInterface<Cliente> {

  public Cliente registrarCliente(
          Cliente cliente
  ) throws EntityConstraintException;

  public Cliente registrarCliente(
          String nombre, String apellido, String correo, String contrasena, String telefono, String direccion
  ) throws EntityConstraintException;

  public void verificarCredenciales(
      Cliente cliente
  ) throws InvalidCredentialsException, EntityNotFoundException;

  public void verificarCredenciales(
          String correo, String contrasena
  ) throws  InvalidCredentialsException, EntityNotFoundException;
}
