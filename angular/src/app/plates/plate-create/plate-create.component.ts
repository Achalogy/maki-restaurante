import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/interfaces/category.interface';
import { Plate } from 'src/app/interfaces/plate.interface';
import { CategoryService } from 'src/app/service/category.service';
import { PlateService } from 'src/app/service/plate.service';

@Component({
  selector: 'app-plate-create',
  templateUrl: './plate-create.component.html',
  styleUrls: ['./plate-create.component.css']
})
export class PlateCreateComponent {
  plate: Plate = {
    id: 0,
    name: '',
    description: '',
    price: 0,
    urlImage: '',
    category: { id: 0, name: "" },
    available: true
  };

  categoryList: Category[] = [];

  constructor(
    public plateService: PlateService,
        private categoryService: CategoryService,
    private router: Router
  ) {

  }
  

  ngOnInit() {
    this.categoryService.selectAll().subscribe(cats => this.categoryList = cats);
    if(!this.plate.category) {
      this
    }
  }

  savePlate() {
    this.plate.id = Math.floor(Math.random() * 1000);
    console.log('Plato guardado:', this.plate);

    this.plateService.create(this.plate).subscribe(() => {
      this.router.navigate(['/plate/crud']);
    })

  }

  resetForm() {
    this.plate = {
      id: 0,
      name: '',
      description: '',
      price: 0,
      urlImage: '',
      category: { id: 0, name: "" },
      available: true
    };
  }
}
