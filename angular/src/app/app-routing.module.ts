import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PlateTableComponent } from './plates/plate-table/plate-table.component';
import { PlateEditComponent } from './plates/plate-edit/plate-edit.component';
import { PlateCreateComponent } from './plates/plate-create/plate-create.component';
import { PlateViewComponent } from './plates/plate-view/plate-view.component';
import { PlateMenuComponent } from './plates/plate-menu/plate-menu.component';
import { LandingPageComponent } from './landing/landing-page/landing-page.component';
import { OperatorFormComponent } from './operators/operator-form/operator-form.component';
import { OperatorCrudComponent } from './operators/operator-crud/operator-crud.component';
import { OperatorViewComponent } from './operators/operator-view/operator-view.component';
import { ClientSessionComponent } from './clients/client-session/client-session.component';
import { ClientLogInComponent } from './clients/client-log-in/client-log-in.component';
import { ClientSignUpComponent } from './clients/client-sign-up/client-sign-up.component';
import { ClientEditComponent } from './clients/client-edit/client-edit.component';
import { ClientCrudComponent } from './clients/client-crud/client-crud.component';
import { GatewayComponent } from './admin/gateway/gateway.component';

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  
  { path: 'plate/crud', component: PlateTableComponent },
  { path: 'plate/:id/edit', component: PlateEditComponent },
  { path: 'plate/create', component: PlateCreateComponent },
  { path: 'plate/menu', component: PlateMenuComponent },
  { path: 'plate/:id', component: PlateViewComponent },

  { path: 'operator/crud', component: OperatorCrudComponent },
  { path: 'operator/:id/edit', component: OperatorFormComponent },
  { path: 'operator/create', component: OperatorFormComponent },
  { path: 'operator/:id', component: OperatorViewComponent },

  
  {path: "client/log-in", component: ClientLogInComponent},
  {path: "client/sign-up", component: ClientSignUpComponent},
  {path: "client/crud", component: ClientCrudComponent},
  {path: "client/edit/:id", component: ClientEditComponent },
  {path: "client/:id", component: ClientSessionComponent },
  
  {path: "admin", component: GatewayComponent },
  
  { path: '**', pathMatch: 'full', redirectTo: '' },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
