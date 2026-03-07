package com.maki.web.repository;

import java.util.*;

import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.Categoria;

@Repository
public class CategoriaRepository implements RepositoryInterface<Categoria> {
    private Map<Long, Categoria> categorias = new HashMap<>();

    public CategoriaRepository() {
    }

    @Override
    public Collection<Categoria> selectAll() {
        return categorias.values();
    }

    @Override
    public Categoria selectById(Long id) throws EntityNotFoundException {
        Categoria c = categorias.get(id);

        if(c == null) throw new EntityNotFoundException("No hay categoría con id=" + id);
        return c;
    }

    @Override
    public Categoria insert(Categoria categoria) throws EntityConstraintException {
        Categoria old = categorias.get(categoria.getId());
        if(old != null) throw new EntityConstraintException("Ya exite una categoría con este id");
        if(categoria.getId() == null) {
            List<Long> ids = new ArrayList<>(categorias.keySet());
            Long latestId = Collections.max(ids);
            categoria.setId(latestId +1);
        }

        categorias.put(categoria.getId(), categoria);
        return categoria;
    }

    @Override
    public void delete(Categoria categoria) throws EntityNotFoundException {
        if(categorias.get(categoria.getId()) == null) throw new EntityNotFoundException("No existe una categoría con este id");

        categorias.remove(categoria.getId());
    }

    @Override
    public void deleteByID(Long id) throws EntityNotFoundException {
        if(categorias.get(id) == null) throw new EntityNotFoundException("No existe una categoría con este id");

        categorias.remove(id);
    }

    @Override
    public Categoria update(Categoria categoria) throws EntityConstraintException, EntityNotFoundException {
        if(categorias.get(categoria.getId()) == null) throw new EntityNotFoundException("No existe una categoría con este id");

        categorias.put(categoria.getId(), categoria);
        return categoria;
    }

    @Override
    public Categoria upsert(Categoria categoria) throws EntityConstraintException {
        if(categoria.getId() == null) {
            List<Long> ids = new ArrayList<>(categorias.keySet());
            Long latestId = Collections.max(ids);
            categoria.setId(latestId + 1);
        }

        categorias.put(categoria.getId(), categoria);
        return categoria;
    }
}