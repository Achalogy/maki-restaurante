package com.maki.web.service;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maki.web.entities.Plato;
import com.maki.web.repository.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Override
    public Collection<Plato> getMenu() {
        return menuRepository.getMenu();
    }

    @Override
    public Plato getById(Integer id) {
        return menuRepository.getById(id);
    }

    @Override
    public void addPlato(Plato plato) {
        menuRepository.addPlato(plato);
    }

    @Override
    public void deletePlato(Integer id) {
        menuRepository.deletePlato(id);
    }   

}
