import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AdditionalCategory } from 'src/app/interfaces/additional-category.interface';
import { Additional } from 'src/app/interfaces/additional.interface';
import { Category } from 'src/app/interfaces/category.interface';
import { AdditionalService } from 'src/app/service/data/additional.service';

@Component({
  selector: 'app-additional-crud',
  templateUrl: './additional-crud.component.html',
  styleUrls: ['./additional-crud.component.css']
})
export class AdditionalCrudComponent {

  additionalList: Additional[] = []
  additionalCategories: AdditionalCategory[] = []
  catsByAdId: Record<number, Category[]> = {}

  constructor(
    private router: Router,
    private additionalService: AdditionalService
  ) {}

  updateLists() {
    this.additionalService.selectAll().subscribe(additionals =>
        this.additionalList = additionals
    )
    
    this.additionalService.selectAllCategories().subscribe(adcs => {
      this.additionalCategories = adcs
      this.catsByAdId = {}

      for(const a of adcs) {
        if(!a.additional) continue;
        this.catsByAdId[a.additional.id] ??= []
        this.catsByAdId[a.additional.id].push(a.category);
      }

      console.log(this.catsByAdId)
    })
  }

  ngOnInit() {
    this.updateLists();
  }
  
  deleteAdditional(id: number) {
    if (confirm(
      '¿Estás seguro de que deseas eliminar este adicional?',
    )) {
      this.additionalService.delete(id).subscribe(() => {
        this.updateLists();
      })
    }
  }

  editAdditional(id: number) {
    this.router.navigate([`/additional/${id}/edit`])
  }

  createAdditional() {
    this.router.navigate(["/additional/create"])
  }
}
