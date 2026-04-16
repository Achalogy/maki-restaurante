import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Aditional } from 'src/app/interfaces/aditional.interface';
import { Category } from 'src/app/interfaces/category.interface';
import { AditionalService } from 'src/app/service/aditional.service';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-aditional-form',
  templateUrl: './aditional-form.component.html',
  styleUrls: ['./aditional-form.component.css']
})
export class AditionalFormComponent {
  // Inicializamos un objeto limpio
  aditional: Aditional = {
    name: "",
    price: 0,
    id: -1
  };

  categoryList: Category[] = [];
  selectedCategoryList: Category[] = [];

  constructor(
    public router: Router,
    private categoryService: CategoryService,
    private aditionalService: AditionalService
  ) { }

  ngOnInit() {
    this.categoryService.selectAll().subscribe((cats) => {
      this.categoryList = cats;
    });
  }

  compareCategory(c1: Category, c2: Category): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  saveAditional() {
    this.aditionalService.create(this.aditional).subscribe((newAditional) => {
      if (this.selectedCategoryList.length > 0) {
        this.aditionalService.setCategories(newAditional.id, this.selectedCategoryList).subscribe(() => {
          this.router.navigate(['/aditional/crud']); // Redirigir al terminar
        });
      } else {
        this.router.navigate(['/aditional/crud']);
      }
    });
  }
}
