import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { OrderStatus, PurchaseOrder } from 'src/app/interfaces/purchase-order.interface';
import { PurchaseOrderService } from 'src/app/service/data/purchase-order.service';
import translateStatus from 'src/utils/translateStatus';

@Component({
  selector: 'app-purchase-order-crud',
  templateUrl: './purchase-order-crud.component.html',
  styleUrls: ['./purchase-order-crud.component.css']
})
export class PurchaseOrderCrudComponent {
  purchaseOrderList: PurchaseOrder[] = [];
  constructor(
      private purchaseOrderService: PurchaseOrderService,
          private router: Router
    ) {
  
    }
    ngOnInit() {
      this.purchaseOrderService.selectAll().subscribe(
        (purchaseOrders) => {
          this.purchaseOrderList = purchaseOrders;
        }
      )
    }

    deletePurchaseOrder(id: number){
      if (confirm('¿Estás seguro de que deseas eliminar este pedido?')) {
        this.purchaseOrderService.delete(id).subscribe(() => {
          this.purchaseOrderService.selectAll().subscribe(purchaseOrders => this.purchaseOrderList = purchaseOrders) 
        })
      }

    }

    //Juan must work on this, this is missing
    goToPurchaseOrder(id: number) {
      this.router.navigate([`/purchase-order/${id}`])
    }

    navigateTo(url: string) {
      this.router.navigate([url])
    }
    
    translateStatus(status: OrderStatus) {
      return translateStatus(status)
    }
}
