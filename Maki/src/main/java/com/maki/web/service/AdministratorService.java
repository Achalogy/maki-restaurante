package com.maki.web.service;

import com.maki.web.entities.Administrator;
import com.maki.web.exception.InvalidCredentialsException;
import com.maki.web.exception.EntityNotFoundException;

public interface AdministratorService extends ServiceInterface<Administrator> {
    public boolean existsByUsername(String username);
}
