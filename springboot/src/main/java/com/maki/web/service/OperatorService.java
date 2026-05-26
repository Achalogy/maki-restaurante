package com.maki.web.service;

import com.maki.web.entities.Operator;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.exception.InvalidCredentialsException;
import java.util.List;

public interface OperatorService extends ServiceInterface<Operator> {
    Operator selectByUsername(String username) throws EntityNotFoundException;

    List<Operator> searchByNameOrUsername(String term);

    Long countByUsername(String username);

    List<Operator> selectAllOrderedByName();

    Operator verifyCredentials(String username, String password)
            throws InvalidCredentialsException, EntityNotFoundException;
}
