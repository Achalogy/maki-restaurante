package com.maki.web.repository;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Cliente;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ClienteRepository {
    private Map<Integer, Cliente> clientes = new HashMap<>();

    public ClienteRepository() {
        clientes.put(1, new Cliente(1, "Miguel", "Vargas", "acha@acha.dev", "eveyzoe", "+57 314 852 7241", "Cra 123 #24-242B"));
    }

    public Collection<Cliente> getAll() {
        return clientes.values();
    }

    public Cliente getById(Integer id) {
        return clientes.get(id);
    }

    public void addClient(Cliente cliente) {
        if (cliente.getId_cliente() == null) {
            int newId = clientes.size() + 1;
            cliente.setId_cliente(newId);
        }
        clientes.put(cliente.getId_cliente(), cliente);
    }

    public void deleteClient(Integer id) {
        clientes.remove(id);
    }
}
