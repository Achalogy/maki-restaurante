package com.maki.web.service;

import java.util.List;

import com.maki.web.entities.Delivery;

public interface DeliveryService extends ServiceInterface<Delivery> {
  List<Delivery> selectAllActive();
}
