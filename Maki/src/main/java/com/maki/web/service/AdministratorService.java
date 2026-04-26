package com.maki.web.service;

import com.maki.web.entities.Administrator;
import com.maki.web.exception.InvalidCredentialsException;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;

public interface AdministratorService extends ServiceInterface<Administrator> {
    public Administrator verifyCredentials(
            String username, String password) throws InvalidCredentialsException, EntityNotFoundException;
}
