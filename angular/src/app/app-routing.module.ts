import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PlateTableComponent } from './plates/plate-table/plate-table.component';
import { PlateEditComponent } from './plates/plate-edit/plate-edit.component';
import { PlateCreateComponent } from './plates/plate-create/plate-create.component';
import { PlateViewComponent } from './plates/plate-view/plate-view.component';
import { LandingPageComponent } from './landing/landing-page/landing-page.component';
import { OperatorFormComponent } from './operators/operator-form/operator-form.component';

const routes: Routes = [
  { path: 'plate/crud', component: PlateTableComponent },
  { path: 'plate/:id/edit', component: PlateEditComponent },
  { path: 'plate/create', component: PlateCreateComponent },
  { path: 'plate/:id', component: PlateViewComponent },
  { path: '', component: LandingPageComponent },

  { path: 'operator/create', component: OperatorFormComponent },
  { path: 'operator/:id/edit', component: OperatorFormComponent },

  { path: '**', pathMatch: 'full', redirectTo:''}
  
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
