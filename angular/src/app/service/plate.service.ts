import { Injectable } from '@angular/core';
import { Plate } from '../interfaces/plate.interface';

@Injectable({
  providedIn: 'root'
})
export class PlateService {

  constructor() { }

  plateList: Plate[] = [
    {
      id: 1,
      name: 'Sushi Variado',
      price: 51960,
      description: 'Hermosa combinación de nigiri y rollos con los mejores ingredientes.',
      urlImage: 'https://images.unsplash.com/photo-1581781870027-04212e231e96?w=500',
      available: true,
      category: { id: 3, name: 'Sushi' }
    },
    {
      id: 2,
      name: 'Ramen',
      price: 47960,
      description: 'Tazón abundante con caldo tonkotsu rico, tallarines tiernos, huevos y barriga de cerdo marinada.',
      urlImage: 'https://images.unsplash.com/photo-1638866281450-3933540af86a?w=500',
      available: true,
      category: { id: 2, name: 'Platos fuertes' }
    },
    {
      id: 3,
      name: 'Tempura',
      price: 43960,
      description: 'Verduras y camarones fritos hasta obtener una perfección dorada con salsa tradicional.',
      urlImage: 'https://images.unsplash.com/photo-1677743537607-f7fc9273ec4d?w=500',
      available: true,
      category: { id: 1, name: 'Entradas' }
    },
    {
      id: 4,
      name: 'Tonkatsu',
      price: 55960,
      description: 'Chuleta de cerdo premium rebozada en panko y frita hasta quedar dorada y crujiente.',
      urlImage: 'https://images.unsplash.com/photo-1734775373504-ff24ea8419b2?w=500',
      available: true,
      category: { id: 2, name: 'Platos fuertes' }
    },
    {
      id: 5,
      name: 'Gyoza',
      price: 31960,
      description: 'Empanadillas fritas rellenas de cerdo sazonado y verduras, hechas a mano.',
      urlImage: 'https://images.unsplash.com/photo-1738681336104-608b4e7dc3b0?w=500',
      available: true,
      category: { id: 1, name: 'Entradas' }
    },
    {
      id: 6,
      name: 'Edamame',
      price: 23960,
      description: 'Frijoles de soya jóvenes cocidos al vapor, ligeramente salados y ricos en proteína.',
      urlImage: 'https://images.unsplash.com/photo-1575262599410-837a72005862?w=500',
      available: true,
      category: { id: 1, name: 'Entradas' }
    },
    {
      id: 7,
      name: 'Sopa Miso',
      price: 15960,
      description: 'Sopa tradicional japonesa con pasta miso fermentada, tofu y alga marina.',
      urlImage: 'https://images.unsplash.com/photo-1610393069309-2607fcf74146?w=500',
      available: true,
      category: { id: 1, name: 'Entradas' }
    },
    {
      id: 8,
      name: 'Rollo California',
      price: 39960,
      description: 'Rollo al revés con jurel imitado, aguacate y pepino fresco envuelto en arroz.',
      urlImage: 'https://images.unsplash.com/photo-1559410545-0bdcd187e0a6?w=500',
      available: true,
      category: { id: 3, name: 'Sushi' }
    },
    {
      id: 9,
      name: 'Rollo Dragón',
      price: 59960,
      description: 'Rollo especializado con anguila tierna, pepino crujiente y aguacate en la parte superior.',
      urlImage: 'https://images.unsplash.com/photo-1712192674556-4a89f20240c1?w=500',
      available: true,
      category: { id: 3, name: 'Sushi' }
    },
    {
      id: 10,
      name: 'Rollo Philadelphia',
      price: 47960,
      description: 'Rollo premium con salmón ahumado, queso crema suave y pepino fresco.',
      urlImage: 'https://images.unsplash.com/photo-1759646828324-c215a83828ae?w=500',
      available: true,
      category: { id: 3, name: 'Sushi' }
    },
    {
      id: 11,
      name: 'Cheesecake Matcha',
      price: 27960,
      description: 'Cheesecake rico y cremoso con notas terrosas del té matcha verde premium.',
      urlImage: 'https://plus.unsplash.com/premium_photo-1694599325857-24139cf22ace?w=500',
      available: true,
      category: { id: 4, name: 'Postres' }
    },
    {
      id: 12,
      name: 'Moti Helado',
      price: 19960,
      description: 'Pequeñas esferas de arroz glutinoso rellenas de helado cremoso con textura única.',
      urlImage: 'https://www.elespectador.com/resizer/JbZ9LGO_ygDGxdnmvdEh-63br7g=/arc-anglerfish-arc2-prod-elespectador/public/EXYQ4FEM3RBHTPTQ7JNQ5NKREU.jpg',
      available: true,
      category: { id: 4, name: 'Postres' }
    },
    {
      id: 13,
      name: 'Té Verde',
      price: 7960,
      description: 'Té verde tradicional japonés con sabor fresco y ligero.',
      urlImage: 'https://image.tuasaude.com/media/article/yp/dt/beneficios-del-te-verde_17350.jpg',
      available: true,
      category: { id: 5, name: 'Bebidas' }
    },
    {
      id: 14,
      name: 'Sake Tradicional',
      price: 35960,
      description: 'Bebida alcohólica japonesa elaborada con arroz fermentado con notas complejas de sabor.',
      urlImage: 'https://monstersushi.es/blog/wp-content/uploads/2022/04/sake-robata-barcelona-e1637227199971-1024x784-1.png',
      available: true,
      category: { id: 5, name: 'Bebidas' }
    },
    {
      id: 15,
      name: 'Pollo Teriyaki',
      price: 51960,
      description: 'Pecho de pollo tierno esmaltado con salsa teriyaki brillante y caramelizada.',
      urlImage: 'https://images.unsplash.com/photo-1609183480237-ccbb2d7c5772?w=500',
      available: true,
      category: { id: 2, name: 'Platos fuertes' }
    }
  ];

  selectAll() {
    
    return this.plateList
  }

  selectById(id: number) {
    return this.plateList.find(x => x.id == id)
  }

  update(id: number, data: Partial<Plate>) {
    const i = this.plateList.findIndex(x => x.id == id)
    if(i === -1) return;

    this.plateList[i] = {
      ...this.plateList[i],
      ...data
    }
  }

  delete(id: number) {
    this.plateList = this.plateList.filter(x => x.id != id)
  }

  create(data: Plate) {
    if(!data.id) {
      data.id = this.plateList.length > 0 ? Math.max(...this.plateList.map(x => x.id)) + 1 : 1
    }

    this.plateList.push(data)
  }
}
