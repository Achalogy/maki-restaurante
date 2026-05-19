import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { PlateTableComponent } from "./pages/plates/plate-table/plate-table.component";
import { PlateEditComponent } from "./pages/plates/plate-edit/plate-edit.component";
import { PlateCreateComponent } from "./pages/plates/plate-create/plate-create.component";
import { PlateViewComponent } from "./pages/plates/plate-view/plate-view.component";
import { PlateMenuComponent } from "./pages/plates/plate-menu/plate-menu.component";
import { LandingPageComponent } from "./pages/landing/landing-page/landing-page.component";
import { OperatorFormComponent } from "./pages/operators/operator-form/operator-form.component";
import { OperatorCrudComponent } from "./pages/operators/operator-crud/operator-crud.component";
import { OperatorViewComponent } from "./pages/operators/operator-view/operator-view.component";
import { ClientSessionComponent } from "./pages/clients/client-session/client-session.component";
import { ClientLogInComponent } from "./pages/clients/client-log-in/client-log-in.component";
import { ClientSignUpComponent } from "./pages/clients/client-sign-up/client-sign-up.component";
import { ClientEditComponent } from "./pages/clients/client-edit/client-edit.component";
import { ClientCrudComponent } from "./pages/clients/client-crud/client-crud.component";
import { GatewayComponent } from "./pages/admin/gateway/gateway.component";
import { ClientProfileComponent } from "./pages/clients/client-profile/client-profile.component";
import { AdditionalCrudComponent } from "./pages/additionals/additional-crud/additional-crud.component";
import { AdditionalFormComponent } from "./pages/additionals/additional-form/additional-form.component";
import { AdditionalEditComponent } from "./pages/additionals/additional-edit/additional-edit.component";
import { PurchaseOrderCrudComponent } from "./pages/purchase-order/purchase-order-crud/purchase-order-crud.component";
import { OperatorLogInComponent } from "./pages/operators/operator-log-in/operator-log-in.component";
import { PurchaseOrderViewComponent } from "./pages/purchase-order/purchase-order-view/purchase-order-view.component";
import { DeliveryCrudComponent } from "./pages/delivery/delivery-crud/delivery-crud.component";
import { AdminLogInComponent } from "./pages/admin/admin-log-in/admin-log-in.component";
import { PurchaseOrderAdminviewComponent } from "./pages/purchase-order/purchase-order-adminview/purchase-order-adminview.component";
import { ClientOrdersComponent } from "./pages/clients/client-orders/client-orders.component";
import { OperatorGatewayComponent } from "./pages/operators/operator-gateway/operator-gateway.component";

import { LogInComponent } from "./pages/log-in/log-in.component";
import { LogOutComponent } from "./pages/log-out/log-out.component";
import { AuthGuard } from "./service/auth.guard";

const routes: Routes = [
  { path: "", component: LandingPageComponent },

  { path: "log-in", component: LogInComponent },
  { path: "client/log-in", redirectTo: "log-in", pathMatch: "full" },
  { path: "operator/log-in", redirectTo: "log-in", pathMatch: "full" },
  { path: "admin/log-in", redirectTo: "log-in", pathMatch: "full" },

  {
    path: "plate/crud",
    component: PlateTableComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },
  {
    path: "plate/:id/edit",
    component: PlateEditComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },
  {
    path: "plate/create",
    component: PlateCreateComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },
  { path: "plate/menu", component: PlateMenuComponent },
  { path: "plate/:id", component: PlateViewComponent },

  {
    path: "operator/crud",
    component: OperatorCrudComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },
  {
    path: "operator/gateway",
    component: OperatorGatewayComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN", "OPERATOR"] },
  },
  {
    path: "operator/create",
    component: OperatorFormComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },
  {
    path: "operator/:id/edit",
    component: OperatorFormComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },
  {
    path: "operator/:id",
    component: OperatorViewComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },

  { path: "client/sign-up", component: ClientSignUpComponent },
  {
    path: "client/crud",
    component: ClientCrudComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },
  {
    path: "client/edit/:id",
    component: ClientEditComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN", "CLIENT"] },
  },
  {
    path: "client/:id",
    component: ClientSessionComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN", "CLIENT"] },
  },
  {
    path: "client/profile/:id",
    component: ClientProfileComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN", "CLIENT"] },
  },
  {
    path: "client/orders/:id",
    component: ClientOrdersComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN", "CLIENT"] },
  },

  {
    path: "additional/crud",
    component: AdditionalCrudComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },
  {
    path: "additional/create",
    component: AdditionalFormComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },
  {
    path: "additional/:id/edit",
    component: AdditionalEditComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },

  {
    path: "admin",
    component: GatewayComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },
  {
    path: "purchase-order/crud",
    component: PurchaseOrderCrudComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN", "OPERATOR"] },
  },
  {
    path: "purchase-order/adminview",
    component: PurchaseOrderAdminviewComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN"] },
  },
  {
    path: "purchase-order/:id",
    component: PurchaseOrderViewComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN", "OPERATOR", "CLIENT"] },
  },

  {
    path: "delivery/crud",
    component: DeliveryCrudComponent,
    canActivate: [AuthGuard],
    data: { roles: ["ADMIN", "OPERATOR"] },
  },

  { path: "log-out", component: LogOutComponent },

  { path: "**", pathMatch: "full", redirectTo: "" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
