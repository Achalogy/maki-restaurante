import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PlateTableComponent } from './plates/plate-table/plate-table.component';
import { PlateEditComponent } from './plates/plate-edit/plate-edit.component';
import { PlateCreateComponent } from './plates/plate-create/plate-create.component';
import { PlateViewComponent } from './plates/plate-view/plate-view.component';
import { LandingPageComponent } from './landing/landing-page/landing-page.component';
import { OperarioFormComponent } from './components/operario-form/operario-form.component';

const routes: Routes = [
  { path: 'plate/crud', component: PlateTableComponent },
  { path: 'plate/:id/edit', component: PlateEditComponent },
  { path: 'plate/create', component: PlateCreateComponent },
  { path: 'plate/:id', component: PlateViewComponent },
  { path: '', component: LandingPageComponent },

  { path: 'operarios/crear', component: OperarioFormComponent },
  { path: 'operarios/editar/:id', component: OperarioFormComponent },

  { path: '**', pathMatch: 'full', redirectTo:''}
  
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
