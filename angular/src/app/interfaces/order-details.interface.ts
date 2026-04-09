import { Order } from "./order.interface";
import { Plate } from "./plate.interface";

export interface OrderDetails {
  id: number,
  order: Order,
  product: Plate,
  quantity: number
}