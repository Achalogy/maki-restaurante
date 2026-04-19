import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Additional } from 'src/app/interfaces/additional.interface';
import { Category } from 'src/app/interfaces/category.interface';
import { AdditionalService } from 'src/app/service/additional.service';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-additional-edit',
  templateUrl: './additional-edit.component.html',
  styleUrls: ['./additional-edit.component.css']
})
export class AdditionalEditComponent implements OnInit {
  additional: Additional = {
    name: "",
    price: 0,
    id: -1,
  }
  categoryList: Category[] = []
  selectedCategoryList: Category[] = []

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private categoryService: CategoryService,
    private additionalService: AdditionalService
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.additionalService.selectById(id).subscribe(ad => {
      this.additional = ad;
      this.updateLists();
    });
  }

  updateLists() {
    this.categoryService.selectAll().subscribe((cats) => {
      this.categoryList = cats;
    });
    this.additionalService.selectAllCategoriesByAdditionalId(this.additional.id).subscribe((cats) => {
      this.selectedCategoryList = cats.map(c => c.category);
    });
  }

  // 3. Función clave para que Angular sepa qué opciones del <select multiple> marcar
  compareCategory(c1: Category, c2: Category): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  updateAdditional() {
    this.additionalService.update(this.additional.id, this.additional).subscribe(() => {
      this.additionalService.setCategories(this.additional.id, this.selectedCategoryList).subscribe(() => {
        // this.updateLists()
        this.router.navigate(["/additional/crud"])
      })
    })
  }
}