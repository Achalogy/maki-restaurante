import { Component, OnInit } from '@angular/core';
import { PurchaseOrderDetails } from 'src/app/interfaces/order-details.interface';
import { OrderDetailsService } from 'src/app/service/data/order-details.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-purchase-order-view',
  templateUrl: './purchase-order-view.component.html',
  styleUrls: ['./purchase-order-view.component.css']
})
export class PurchaseOrderViewComponent implements OnInit {

  // All detail rows for this order (one per plate)
  orderItems: PurchaseOrderDetails[] = [];

  // Shared order info — taken from the first item
  get orderInfo() {
    return this.orderItems[0]?.order ?? null;
  }

  constructor(
    private orderDetailsService: OrderDetailsService,
    private router: Router
  ) {}

  ngOnInit() {
    const orderId = parseInt(this.router.url.split('/').pop() || '0', 10);
    this.orderDetailsService.selectByOrderId(orderId).subscribe(
      (items: PurchaseOrderDetails[]) => {
        this.orderItems = items;
      }
    );
  }

  // Sum of all plates × quantity + all additionals across every item
  get total(): number {
    return this.orderItems.reduce((acc, item) => {
      const plateTotal = (item.plate?.price ?? 0) * (item.quantity ?? 1);
      const addTotal = (item.additionals ?? [])
        .reduce((s: number, a: any) => s + (a.additional?.price ?? 0), 0);
      return acc + plateTotal + addTotal;
    }, 0);
  }
}