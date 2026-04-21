package com.maki.web.service;

import com.maki.web.entities.Operator;
import com.maki.web.entities.PurchaseOrder;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.exception.InvalidCredentialsException;
import com.maki.web.repository.OperatorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {

  @Autowired
  private OperatorRepository repo;

  @Autowired
  private PurchaseOrderService pedidoService;

  @Override
  public List<Operator> selectAll() {
    return repo.findAll();
  }

  @Override
  public Operator selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Operator no encontrado con ID: " + id));
  }

  @Override
  public Operator insert(Operator entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de Operator no debe incluir un ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Operator entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    Operator Operator = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Operator no encontrado para eliminar: " + id));

    // Desvincular pedidos antes de borrar al Operator para mantener integridad
    for (PurchaseOrder p : pedidoService.selectAll()) {
      if (p.getOperator() != null && p.getOperator().getId().equals(id)) {
        p.setOperator(null);
        pedidoService.update(p);
      }
    }

    repo.delete(Operator);
  }

  @Override
  public Operator update(Operator entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Operator no encontrado");
    }
    return repo.save(entity);
  }

  @Override
  public Operator verifyCredentials(String username, String password)
      throws InvalidCredentialsException, EntityNotFoundException {
    Operator repoOperator = repo.findByUsername(username)
        .orElseThrow(
            () -> new EntityNotFoundException("No existe un operator registrado con el username: " + username));

    if (!repoOperator.getPassword().equals(password)) {
      throw new InvalidCredentialsException("Credenciales inválidas");
    }

    return repoOperator;
  }
}