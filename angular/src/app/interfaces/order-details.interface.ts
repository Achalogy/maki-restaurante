import { PurchaseOrder } from "./purchase-order.interface";
import { Plate } from "./plate.interface";

export interface PurchaseOrderDetails {
  id: number,
  order: PurchaseOrder,
  plate: Plate,
  quantity: number
  additionals?: { additional: { name: string; price: number } }[]
}