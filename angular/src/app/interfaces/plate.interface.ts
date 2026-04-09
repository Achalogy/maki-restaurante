import { Category } from "./category.interface";

export interface Plate {
  id: number;
  name: string;
  price: number;
  description: string;
  urlImage: string;
  available: boolean;
  category: Category;
}