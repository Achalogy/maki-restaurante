import { PurchaseOrder } from "./purchase-order.interface";
import { Plate } from "./plate.interface";
import { Additional } from "./additional.interface";

export interface PurchaseOrderDetails {
  id: number,
  order: PurchaseOrder,
  plate: Plate,
  quantity: number
  additionals?: { additional: { name: string; price: number } }[]
}