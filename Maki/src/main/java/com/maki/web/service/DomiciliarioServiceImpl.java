package com.maki.web.service;

import com.maki.web.entities.Delivery;
import com.maki.web.entities.Order;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.DomiciliarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomiciliarioServiceImpl implements DomiciliarioService {

  @Autowired
  private DomiciliarioRepository repo;

  @Autowired
  private PedidoService pedidoService;

  @Override
  public List<Delivery> selectAll() {
    return repo.findAll();
  }

  @Override
  public Delivery selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Domiciliario no encontrado con ID: " + id));
  }

  @Override
  public Delivery insert(Delivery entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de Domiciliario no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Delivery entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    Delivery domiciliario = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Domiciliario no encontrado para eliminar: " + id));

    // Lógica de integridad: Desvincular pedidos antes de borrar al domiciliario
    for (Order p : pedidoService.selectAll()) {
      if (p.getDomiciliario() != null && p.getDomiciliario().getId().equals(id)) {
        p.setDomiciliario(null);
        pedidoService.update(p);
      }
    }

    repo.delete(domiciliario);
  }

  @Override
  public Delivery update(Delivery entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Domiciliario no encontrado");
    }
    return repo.save(entity);
  }
}