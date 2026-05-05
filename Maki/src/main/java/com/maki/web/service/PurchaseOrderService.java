package com.maki.web.service;

import java.util.List;

import com.maki.web.entities.Delivery;
import com.maki.web.entities.PlateWithAdditionals;
import com.maki.web.entities.PurchaseOrder;
import com.maki.web.exception.EntityConstraintException;



public interface PurchaseOrderService extends ServiceInterface<PurchaseOrder> {
  PurchaseOrder createPurchaseOrderFromcart(Long id, List<PlateWithAdditionals> plates);
  List<PurchaseOrder> selectNotCompleted();
  PurchaseOrder setDelivery(Long id, Delivery delivery) throws EntityConstraintException;
}
