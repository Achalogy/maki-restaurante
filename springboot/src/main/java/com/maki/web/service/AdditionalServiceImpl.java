package com.maki.web.service;

import com.maki.web.entities.Additional;
import com.maki.web.entities.AdditionalCategory;
import com.maki.web.entities.AdditionalOrderDetails;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.AdditionalCategoryRepository;
import com.maki.web.repository.AdditionalOrderDetailsRepository;
import com.maki.web.repository.AdditionalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdditionalServiceImpl implements AdditionalService {

    @Autowired private AdditionalRepository repo;

    @Autowired private AdditionalCategoryRepository additionalCategoryRepo;

    @Autowired private AdditionalOrderDetailsRepository additionalOrderDetailsRepo;

    AdditionalServiceImpl() {}

    @Override
    public List<Additional> selectAll() {
        return repo.findAll();
    }

    @Override
    public Additional selectById(Long id) throws EntityNotFoundException {
        return repo.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Adicional no encontrado con ID: " + id));
    }

    @Override
    public Additional insert(Additional entity) throws EntityConstraintException {
        if (entity.getId() != null) {
            entity.setId(null);
            // throw new EntityConstraintException("El insert de Adicional no debe incluir un ID");
        }
        return repo.save(entity);
    }

    @Override
    public void delete(Additional entity) throws EntityNotFoundException {
        deleteByID(entity.getId());
    }

    @Override
    public void deleteByID(Long id) throws EntityNotFoundException {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException(
                    "No se puede eliminar: Adicional no existe con ID: " + id);
        }

        for (AdditionalCategory a : additionalCategoryRepo.findAll()) {
            if (a.getAdditional() != null && a.getAdditional().getId() == id) {
                additionalCategoryRepo.delete(a);
            }
        }
        for (AdditionalOrderDetails a : additionalOrderDetailsRepo.findAll()) {
            if (a.getAdditional() != null && a.getAdditional().getId() == id) {
                additionalOrderDetailsRepo.delete(a);
            }
        }

        repo.deleteById(id);
    }

    @Override
    public Additional update(Additional entity)
            throws EntityConstraintException, EntityNotFoundException {
        if (entity.getId() == null || !repo.existsById(entity.getId())) {
            throw new EntityNotFoundException("No se puede actualizar: Adicional no encontrado");
        }
        return repo.save(entity);
    }
}
