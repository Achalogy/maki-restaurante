package com.maki.web.service;
import java.util.Collection;
import com.maki.web.entities.Plato;

public interface MenuService {
    
    public Collection<Plato> getMenu();

    public Plato getById(Integer id);
}
