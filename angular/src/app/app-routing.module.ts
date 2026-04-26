import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PlateTableComponent } from './pages/plates/plate-table/plate-table.component';
import { PlateEditComponent } from './pages/plates/plate-edit/plate-edit.component';
import { PlateCreateComponent } from './pages/plates/plate-create/plate-create.component';
import { PlateViewComponent } from './pages/plates/plate-view/plate-view.component';
import { PlateMenuComponent } from './pages/plates/plate-menu/plate-menu.component';
import { LandingPageComponent } from './pages/landing/landing-page/landing-page.component';
import { OperatorFormComponent } from './pages/operators/operator-form/operator-form.component';
import { OperatorCrudComponent } from './pages/operators/operator-crud/operator-crud.component';
import { OperatorViewComponent } from './pages/operators/operator-view/operator-view.component';
import { ClientSessionComponent } from './pages/clients/client-session/client-session.component';
import { ClientLogInComponent } from './pages/clients/client-log-in/client-log-in.component';
import { ClientSignUpComponent } from './pages/clients/client-sign-up/client-sign-up.component';
import { ClientEditComponent } from './pages/clients/client-edit/client-edit.component';
import { ClientCrudComponent } from './pages/clients/client-crud/client-crud.component';
import { GatewayComponent } from './pages/admin/gateway/gateway.component';
import { ClientProfileComponent } from './pages/clients/client-profile/client-profile.component';
import { AdditionalCrudComponent } from './pages/additionals/additional-crud/additional-crud.component';
import { AdditionalFormComponent } from './pages/additionals/additional-form/additional-form.component';
import { AdditionalEditComponent } from './pages/additionals/additional-edit/additional-edit.component';
import { PurchaseOrderCrudComponent } from './pages/purchase-order/purchase-order-crud/purchase-order-crud.component';
import { OperatorLogInComponent } from './pages/operators/operator-log-in/operator-log-in.component';
import { PurchaseOrderViewComponent } from './pages/purchase-order/purchase-order-view/purchase-order-view.component';
import { DeliveryCrudComponent } from './pages/delivery/delivery-crud/delivery-crud.component';
import { AdminLogInComponent } from './pages/admin/admin-log-in/admin-log-in.component';
import { PurchaseOrderAdminviewComponent } from './pages/purchase-order/purchase-order-adminview/purchase-order-adminview.component';

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  
  { path: 'plate/crud', component: PlateTableComponent },
  { path: 'plate/:id/edit', component: PlateEditComponent },
  { path: 'plate/create', component: PlateCreateComponent },
  { path: 'plate/menu', component: PlateMenuComponent },
  { path: 'plate/:id', component: PlateViewComponent },

  { path: 'operator/crud', component: OperatorCrudComponent },
  { path: 'operator/log-in', component: OperatorLogInComponent },
  { path: 'operator/:id/edit', component: OperatorFormComponent },
  { path: 'operator/create', component: OperatorFormComponent },
  { path: 'operator/:id', component: OperatorViewComponent },
  
  
  {path: "client/log-in", component: ClientLogInComponent},
  {path: "client/sign-up", component: ClientSignUpComponent},
  {path: "client/crud", component: ClientCrudComponent},
  {path: "client/edit/:id", component: ClientEditComponent },
  {path: "client/:id", component: ClientSessionComponent },
  {path: "client/profile/:id", component: ClientProfileComponent },
  
  { path: 'additional/crud', component: AdditionalCrudComponent },
  { path: 'additional/create', component: AdditionalFormComponent },
  { path: 'additional/:id/edit', component: AdditionalEditComponent },

  { path: 'admin/log-in', component: AdminLogInComponent },
  {path: "admin", component: GatewayComponent },
  {path: "purchase-order/crud", component: PurchaseOrderCrudComponent },
  {path: "purchase-order/adminview", component: PurchaseOrderAdminviewComponent },
  {path: "purchase-order/:id", component: PurchaseOrderViewComponent },

  { path: 'delivery/crud', component: DeliveryCrudComponent },

  { path: '**', pathMatch: 'full', redirectTo: '' },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
