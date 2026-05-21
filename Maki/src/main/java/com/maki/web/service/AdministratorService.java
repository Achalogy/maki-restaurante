package com.maki.web.service;

import com.maki.web.entities.Administrator;
import com.maki.web.exception.InvalidCredentialsException;
import com.maki.web.exception.EntityNotFoundException;

/**
 * CORRECCIÓN: Se agrega verifyCredentials que faltaba.
 * El AdministratorController llama a este método en el login.

 */
public interface AdministratorService extends ServiceInterface<Administrator> {

    boolean existsByUsername(String username);

    Administrator verifyCredentials(String username, String password)
            throws InvalidCredentialsException, EntityNotFoundException;
}