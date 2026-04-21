import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-food-menu-grid-card',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './food-menu-grid-card.component.html',
  styleUrls: ['./food-menu-grid-card.component.css'],
})
export class FoodMenuGridCardComponent {
  @Input() href!: string;
  @Input() src!: string;
  @Input() label!: string;
  @Input() alt: string = '';
}
