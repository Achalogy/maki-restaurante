import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Additional } from 'src/app/interfaces/additional.interface';
import { Plate } from 'src/app/interfaces/plate.interface';
import { AdditionalService } from 'src/app/service/data/additional.service';
import { PlateService } from 'src/app/service/data/plate.service';
import { ShoppingCartService } from 'src/app/service/ui/shopping-card.service';

@Component({
  selector: 'app-plate-view',
  templateUrl: './plate-view.component.html',
  styleUrls: ['./plate-view.component.css']
})
export class PlateViewComponent {
  plate?: Plate;
  specialInstructions: string = '';

  selectedAdditionals: number[] = [];

  additionalsList: Additional[] = [];

  constructor(
    private route: ActivatedRoute,
    private plateService: PlateService,
    private additionalService: AdditionalService,
    private cartService: ShoppingCartService
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log(id)
    if (id) {
      this.plateService.selectById(id).subscribe((plate) => {
        this.plate = plate;
        // Aquí podrías cargar los adicionales basados en plate.category.id
        this.loadAdditionals(plate.category.id);
      });
    }
  }

  loadAdditionals(categoryId: number) {

    this.additionalService.selectByCategoryId(categoryId).subscribe(additionals => {
      this.additionalsList = additionals
      console.log(this.additionalsList)
    })
    
  }

  onCheckboxChange(id: number, event: any) {
    if (event.target.checked) {
      this.selectedAdditionals.push(id);
    } else {
      this.selectedAdditionals = this.selectedAdditionals.filter(aid => aid !== id);
    }
  }

  addToOrder() {
    if(this.plate)
      this.cartService.addItem(this.plate)
    alert('Plato agregado al pedido');
  }
}
