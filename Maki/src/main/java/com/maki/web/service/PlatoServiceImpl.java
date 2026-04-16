package com.maki.web.service;

import com.maki.web.entities.Category;
import com.maki.web.entities.OrderDetails;
import com.maki.web.entities.Plate;
import com.maki.web.repository.PlatoRepository;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class PlatoServiceImpl implements PlatoService {

  @Autowired
  private PlatoRepository repo;

  @Autowired
  private PedidoDetallesService pedidoDetallesService;

  @Override
  public List<Plate> selectAll() {
    return repo.findAll();
  }

  @Override
  public Plate selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado con ID: " + id));
  }

  @Override
  public Plate insert(Plate entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de Plato no debe incluir un ID");
    }
    return repo.save(entity);
  }

  @Override
  @Transactional
  public Plate update(Plate entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Plato no existe");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Plate entity) throws EntityNotFoundException {
    if (entity == null || entity.getId() == null) {
      throw new EntityNotFoundException("Plato inválido para eliminar");
    }
    this.deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    if (!repo.existsById(id)) {
      throw new EntityNotFoundException("No se puede eliminar: Plato no encontrado con ID: " + id);
    }

    for( OrderDetails pd: pedidoDetallesService.selectAll()) {
      if(pd.getPlate() != null && pd.getPlate().getId().equals(id)) {
        pedidoDetallesService.delete(pd);
      }
    }

    repo.deleteById(id);
  }

  @Override
  @Transactional
  public void cambiarCategoria(Category categoria, Long platoId) throws EntityNotFoundException {
    Plate plato = this.selectById(platoId);

    plato.setCategory(categoria);
    
    repo.save(plato);
  }
}