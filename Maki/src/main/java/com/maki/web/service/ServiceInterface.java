package com.maki.web.service;

import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;

import java.util.Collection;

public interface ServiceInterface<T> {
  public Collection<T> selectAll();
  public T selectById(Long id) throws EntityNotFoundException;
  public T insert(T entity) throws EntityConstraintException;
  public void delete(T entity) throws EntityNotFoundException;
  public void deleteByID(Long id) throws EntityNotFoundException;
  public T update(T entity) throws  EntityConstraintException, EntityNotFoundException;
  public T upsert(T entity) throws  EntityConstraintException;


}
