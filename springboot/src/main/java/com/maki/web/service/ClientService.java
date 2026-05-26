package com.maki.web.service;

import com.maki.web.entities.Client;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.exception.InvalidCredentialsException;

/**
 * CORRECCIÓN: Se agregan findByEmail y verifyCredentials que faltaban. El ClientController los
 * llama en /me y /log-in.
 */
public interface ClientService extends ServiceInterface<Client> {

    boolean existsByEmail(String email);

    Client findByEmail(String email) throws EntityNotFoundException;

    Client verifyCredentials(String email, String password)
            throws InvalidCredentialsException, EntityNotFoundException;
}
