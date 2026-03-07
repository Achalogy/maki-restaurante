package com.maki.web.repository;

import com.maki.web.entities.Cliente;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ClienteRepository implements RepositoryInterface<Cliente> {
    private Map<Long, Cliente> clientes = new HashMap<>();

    public ClienteRepository() {
    }

    @Override
    public Collection<Cliente> selectAll() {
        return clientes.values();
    }

    @Override
    public Cliente selectById(Long id) throws EntityNotFoundException {
        Cliente c = clientes.get(id);

        if(c == null) throw  new EntityNotFoundException("No hay cliente con id=" + id);
        return c;
    }

    @Override
    public Cliente insert(Cliente cliente) throws EntityConstraintException {
        Cliente old = clientes.get(cliente.getId());
        if(old != null) throw new EntityConstraintException("Ya existe un cliente con este id");

        Optional<Cliente> sameEmail = clientes.values().stream().filter(c -> c.getCorreo().equals(cliente.getCorreo()))
                .findFirst();
        if(sameEmail.isPresent()) throw  new EntityConstraintException("Ya existe un cliente con este correo");

        if(cliente.getId() == null) {
            List<Long> ids = new ArrayList<>(clientes.keySet());
            Long latestId = Collections.max(ids);
            cliente.setId(latestId +1);
        }
        
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public void delete(Cliente cliente) throws EntityNotFoundException {
        if(clientes.get(cliente.getId()) == null) throw new EntityNotFoundException("No existe un cliente con este id");

        clientes.remove(cliente.getId());
    }

    @Override
    public void deleteByID(Long id) throws EntityNotFoundException {
        if(clientes.get(id) == null) throw new EntityNotFoundException("No existe un cliente con este id");

        clientes.remove(id);
    }

    @Override
    public Cliente update(Cliente cliente) throws EntityConstraintException, EntityNotFoundException {
        if(clientes.get(cliente.getId()) == null) throw new EntityNotFoundException("No existe un cliente con este id");

        clientes.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Cliente upsert(Cliente cliente) throws EntityConstraintException {
        if(cliente.getId() == null) {
            List<Long> ids = new ArrayList<>(clientes.keySet());
            Long latestId = Collections.max(ids);
            cliente.setId(latestId +1);
        }

        clientes.put(cliente.getId(), cliente);
        return cliente;
    }
}
