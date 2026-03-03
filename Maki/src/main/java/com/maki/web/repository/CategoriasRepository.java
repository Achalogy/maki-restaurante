package com.maki.web.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.Categoria;
import com.maki.web.entities.Plato;

@Repository
public class CategoriasRepository {
    private Map<Integer, Categoria> categorias = new HashMap<>();

    public CategoriasRepository() {
        categorias.put(1, new Categoria(1, "None"));
    }

    public Collection<Categoria> getAll() {
        return categorias.values();
    }

    public Categoria getById(Integer id) {
        return categorias.get(id);
    }

    public void addCategoria(Categoria categoria) {
        if (categoria.getId() == null) {
            int newId = categorias.size() + 1;
            categoria.setId(newId);
        }
        categorias.put(categoria.getId(), categoria);
    }

    public void deleteCategoria(Integer id) {
        if (id == 1) return;
        categorias.remove(id);
    }
}