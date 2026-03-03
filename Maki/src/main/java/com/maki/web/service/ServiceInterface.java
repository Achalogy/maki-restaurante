package com.maki.web.service;

import com.maki.web.repository.exception.EntityConstraintException;
import com.maki.web.repository.exception.EntityNotFoundException;

import java.util.Collection;

public interface ServiceInterface<T> {
  public Collection<T> selectAll();
  public T selectById(Integer id) throws EntityNotFoundException;
  public T insert(T entity) throws EntityConstraintException;
  public void delete(T entity) throws EntityNotFoundException;
  public void deleteByID(Integer id) throws EntityNotFoundException;
  public T update(T entity) throws  EntityConstraintException, EntityNotFoundException;
  public T upsert(T entity) throws  EntityConstraintException;


}
