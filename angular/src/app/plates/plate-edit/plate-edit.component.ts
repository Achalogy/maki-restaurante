import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/interfaces/category.interface';
import { Plate } from 'src/app/interfaces/plate.interface';
import { CategoryService } from 'src/app/service/category.service';
import { PlateService } from 'src/app/service/plate.service';

@Component({
  selector: 'app-plate-edit',
  templateUrl: './plate-edit.component.html',
  styleUrls: ['./plate-edit.component.css']
})
export class PlateEditComponent {
  plate: Plate = {
    id: 0,
    name: '',
    description: '',
    price: 0,
    urlImage: '',
    category: { id: 0, name: "" },
    available: true
  };

  categoryList: Category[] = []

  constructor(
    private plateService: PlateService,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {

  }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    const p = this.plateService.selectById(id)
    
    p.subscribe(plate => {
      if (!plate)
        this.router.navigate(['/plate/crud']);
      else {
        this.categoryService.selectAll().subscribe(cats => this.categoryList = cats)
        this.plate = plate
      }
    })

  }

  updatePlate() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log('Actualizando plato:', this.plate);
    this.plateService.update(id, this.plate).subscribe(() => {
      this.plateService.updateCategory(id, this.plate.category.id).subscribe(() => {
        this.router.navigate(['/plate/crud']);
      })
    })
    
  }
}
