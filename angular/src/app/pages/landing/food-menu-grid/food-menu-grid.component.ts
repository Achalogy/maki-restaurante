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
    { href: '/plate/menu', src: 'assets/images/food_menu/entradas.jpg',       label: 'Entradas'       },
    { href: '/plate/menu', src: 'assets/images/food_menu/platos_fuertes.jpg', label: 'Platos Fuertes' },
    { href: '/plate/menu', src: 'assets/images/food_menu/sushi.jpg',          label: 'Sushi'          },
    { href: '/plate/menu', src: 'assets/images/food_menu/postres.jpg',        label: 'Postres'        },
    { href: '/plate/menu', src: 'assets/images/food_menu/bebidas.png',        label: 'Bebidas'        },
  ];
}
