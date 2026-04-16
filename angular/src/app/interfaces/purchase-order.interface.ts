import { Client } from "./client.interface";
import { Delivery } from "./delivery.interface";
import { Operator } from "./operator.interface";

export interface PurchaseOrder {
  id: number,
  creation_date: Date,
  delivery_date: Date,
  status: string,
  client: Client,
  domiciliary: Delivery,
  operator: Operator
}