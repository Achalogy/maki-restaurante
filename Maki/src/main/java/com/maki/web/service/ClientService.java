package com.maki.web.service;

import com.maki.web.entities.Client;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.exception.InvalidCredentialsException;

public interface ClientService extends ServiceInterface<Client> {

  public Client registerClient(
          Client client
  ) throws EntityConstraintException;

  public Client registerClient(
          String name, String surname, String email, String password, String phone, String address
  ) throws EntityConstraintException;

  public Client verifyCredentials(
      Client client
  ) throws InvalidCredentialsException, EntityNotFoundException;

  public Client verifyCredentials(
          String email, String password
  ) throws  InvalidCredentialsException, EntityNotFoundException;
}
