import { Additional } from "./additional.interface";
import { Category } from "./category.interface";

export interface AdditionalCategory {
  id: number,
  category: Category,
  additional: Additional
}