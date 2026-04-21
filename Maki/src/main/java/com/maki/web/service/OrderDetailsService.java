package com.maki.web.service;

import com.maki.web.entities.OrderDetails;
import java.util.List;

public interface OrderDetailsService extends ServiceInterface<OrderDetails> {

    List<OrderDetails> findByOrderId(Long orderId);

}
