import { Component, OnInit } from "@angular/core";
import { PurchaseOrderDetails } from "src/app/interfaces/order-details.interface";
import { OrderDetailsService } from "src/app/service/data/order-details.service";
import { Router } from "@angular/router";
import {
  OrderStatus,
  PurchaseOrder,
} from "src/app/interfaces/purchase-order.interface";
import translateStatus from "src/utils/translateStatus";
import { PurchaseOrderService } from "src/app/service/data/purchase-order.service";
import { DeliveryService } from "src/app/service/data/delivery.service";
import { Delivery } from "src/app/interfaces/delivery.interface";

@Component({
  selector: "app-purchase-order-view",
  templateUrl: "./purchase-order-view.component.html",
  styleUrls: ["./purchase-order-view.component.css"],
})
export class PurchaseOrderViewComponent implements OnInit {
  // All detail rows for this order (one per plate)
  orderItems: PurchaseOrderDetails[] = [];
  activeDeliveries: Delivery[] = [];
  purchaseOrder: PurchaseOrder = {} as PurchaseOrder;
  deliveryId = -1;

  // Shared order info — taken from the first item
  get orderInfo() {
    return this.purchaseOrder;
  }

  constructor(
    private orderDetailsService: OrderDetailsService,
    private purchaseOrderService: PurchaseOrderService,
    private deliveryService: DeliveryService,
    private router: Router,
  ) {}

  updateData() {
    const orderId = parseInt(this.router.url.split("/").pop() || "0", 10);
    this.orderDetailsService
      .selectByOrderId(orderId)
      .subscribe((items: PurchaseOrderDetails[]) => {
        this.orderItems = items;
        this.purchaseOrder = items[0].order;
        console.log(items[0]);
        this.deliveryId = items[0].order?.delivery?.id ?? -1;
        this.deliveryService
          .selectActive()
          .subscribe(
            (deliveries) =>
              (this.activeDeliveries = [
                ...deliveries,
                items[0].order.delivery,
              ]),
          );
      });
  }

  ngOnInit() {
    this.updateData();
  }

  // Sum of all plates × quantity + all additionals across every item
  get total(): number {
    return this.orderItems.reduce((acc, item) => {
      const plateTotal = (item.plate?.price ?? 0) * (item.quantity ?? 1);
      const addTotal = (item.additionals ?? []).reduce(
        (s: number, a: { additional: any }) => s + (a.additional?.price ?? 0),
        0,
      );
      return acc + plateTotal + addTotal;
    }, 0);
  }

  translateStatus(status: OrderStatus) {
    return translateStatus(status);
  }

  updateOrder() {
    if (this.deliveryId == -1) {
      this.purchaseOrderService
        .update(this.purchaseOrder.id, this.purchaseOrder)
        .subscribe(() => {
          this.updateData();
        });
    } else {
      this.deliveryService.selectById(this.deliveryId).subscribe((d) => {
        this.purchaseOrder.delivery = d;
        this.purchaseOrderService
          .update(this.purchaseOrder.id, this.purchaseOrder)
          .subscribe(() => {
            this.updateData();
          });
      });
    }
  }
}
