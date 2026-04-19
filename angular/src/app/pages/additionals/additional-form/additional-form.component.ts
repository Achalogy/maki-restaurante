import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Additional } from 'src/app/interfaces/additional.interface';
import { Category } from 'src/app/interfaces/category.interface';
import { AdditionalService } from 'src/app/service/additional.service';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-additional-form',
  templateUrl: './additional-form.component.html',
  styleUrls: ['./additional-form.component.css']
})
export class AdditionalFormComponent {
  // Inicializamos un objeto limpio
  additional: Additional = {
    name: "",
    price: 0,
    id: -1
  };

  categoryList: Category[] = [];
  selectedCategoryList: Category[] = [];

  constructor(
    public router: Router,
    private categoryService: CategoryService,
    private additionalService: AdditionalService
  ) { }

  ngOnInit() {
    this.categoryService.selectAll().subscribe((cats) => {
      this.categoryList = cats;
    });
  }

  compareCategory(c1: Category, c2: Category): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  saveAdditional() {
    this.additionalService.create(this.additional).subscribe((newAdditional) => {
      if (this.selectedCategoryList.length > 0) {
        this.additionalService.setCategories(newAdditional.id, this.selectedCategoryList).subscribe(() => {
          this.router.navigate(['/additional/crud']); // Redirigir al terminar
        });
      } else {
        this.router.navigate(['/additional/crud']);
      }
    });
  }
}
