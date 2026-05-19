package com.maki.web.service;

import com.maki.web.entities.Operator;
import com.maki.web.exception.InvalidCredentialsException;
import com.maki.web.exception.EntityNotFoundException;

import java.util.List;

public interface OperatorService extends ServiceInterface<Operator> {
    public Operator selectByUsername(String username) throws EntityNotFoundException;

    public List<Operator> searchByNameOrUsername(String term);

    public Long countByUsername(String username);

    public List<Operator> selectAllOrderedByName();
}
