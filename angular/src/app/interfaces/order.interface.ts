import { Client } from "./client.interface";
import { Domiciliary } from "./domiciliary.interface";
import { Operator } from "./operator.interface";

export interface Order {
  id: number,
  creation_date: Date,
  delivery_date: Date,
  status: string,
  client: Client,
  domiciliary: Domiciliary,
  operator: Operator
}