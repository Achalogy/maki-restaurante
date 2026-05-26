package com.maki.web.service;

import com.maki.web.entities.Delivery;
import java.util.List;

public interface DeliveryService extends ServiceInterface<Delivery> {
    List<Delivery> selectAllActive();
}
