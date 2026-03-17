package com.maki.web.service;

import com.maki.web.entities.Operador;
import com.maki.web.entities.Pedido;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.OperadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OperadorServiceImpl implements OperadorService {

  @Autowired
  private OperadorRepository repo;

  @Autowired
  private PedidoService pedidoService;

  @Override
  public Collection<Operador> selectAll() {
    return repo.findAll();
  }

  @Override
  public Operador selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Operador no encontrado con ID: " + id));
  }

  @Override
  public Operador insert(Operador entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de Operador no debe incluir un ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Operador entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    Operador operador = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Operador no encontrado para eliminar: " + id));

    // Desvincular pedidos antes de borrar al operador para mantener integridad
    for (Pedido p : pedidoService.selectAll()) {
      if (p.getOperador() != null && p.getOperador().getId().equals(id)) {
        p.setOperador(null);
        pedidoService.update(p);
      }
    }

    repo.delete(operador);
  }

  @Override
  public Operador update(Operador entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Operador no encontrado");
    }
    return repo.save(entity);
  }
}