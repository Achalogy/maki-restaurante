package com.maki.web.service;

import com.maki.web.entities.Client;

public interface ClientService extends ServiceInterface<Client> {
  public boolean existsByEmail(String email);
}
