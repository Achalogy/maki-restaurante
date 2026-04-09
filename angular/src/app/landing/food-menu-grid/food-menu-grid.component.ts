import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FoodMenuGridCardComponent } from '../food-menu-grid-card/food-menu-grid-card.component';

interface MenuItem {
  href: string;
  src: string;
  label: string;
}

@Component({
  selector: 'app-food-menu-grid',
  standalone: true,
  imports: [CommonModule, FoodMenuGridCardComponent],
  templateUrl: './food-menu-grid.component.html',
  styleUrls: ['./food-menu-grid.component.css'],
})
export class FoodMenuGridComponent {
  menuItems: MenuItem[] = [
    { href: '/menu#cat-1', src: 'assets/images/food_menu/entradas.jpg',       label: 'Entradas'       },
    { href: '/menu#cat-2', src: 'assets/images/food_menu/platos_fuertes.jpg', label: 'Platos Fuertes' },
    { href: '/menu#cat-3', src: 'assets/images/food_menu/sushi.jpg',          label: 'Sushi'          },
    { href: '/menu#cat-4', src: 'assets/images/food_menu/postres.jpg',        label: 'Postres'        },
    { href: '/menu#cat-5', src: 'assets/images/food_menu/bebidas.png',        label: 'Bebidas'        },
  ];
}
