import { Client } from "./client.interface";
import { Delivery } from "./delivery.interface";
import { Operator } from "./operator.interface";

export type OrderStatus = "pending" | "preparation" | "sent" | "delivered" | "cancelled"

export interface PurchaseOrder {
  id: number,
  creation_date: Date,
  delivery_date: Date,
  status: OrderStatus,
  client: Client,
  delivery: Delivery,
  operator: Operator
}