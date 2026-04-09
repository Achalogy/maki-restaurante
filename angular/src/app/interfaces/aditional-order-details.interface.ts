import { Aditional } from "./aditional.interface";
import { OrderDetails } from "./order-details.interface";

export interface AditionalOrderDetails {
  id: number,
  details: OrderDetails,
  aditional: Aditional
}