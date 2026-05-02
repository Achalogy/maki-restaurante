package com.maki.web.service;

import com.maki.web.entities.Operator;
import com.maki.web.exception.InvalidCredentialsException;
import com.maki.web.exception.EntityNotFoundException;

public interface OperatorService extends ServiceInterface<Operator> {
    public Operator verifyCredentials(
            String username, String password) throws InvalidCredentialsException, EntityNotFoundException;
}
