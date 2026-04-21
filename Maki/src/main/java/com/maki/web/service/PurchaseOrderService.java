package com.maki.web.service;

import java.util.List;

import com.maki.web.entities.PlateWithAdditionals;
import com.maki.web.entities.PurchaseOrder;



public interface PurchaseOrderService extends ServiceInterface<PurchaseOrder> {
  PurchaseOrder createPurchaseOrderFromcart(Long id, List<PlateWithAdditionals> plates);
}
