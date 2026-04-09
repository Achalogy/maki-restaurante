import { Aditional } from "./aditional.interface";
import { Category } from "./category.interface";

export interface AditionalCategory {
  id: number,
  category: Category,
  aditional: Aditional
}