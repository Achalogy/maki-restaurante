import { Additional } from "./additional.interface";
import { PurchaseOrderDetails } from "./order-details.interface";

export interface AdditionalOrderDetails {
  id: number,
  details: PurchaseOrderDetails,
  additional: Additional
}