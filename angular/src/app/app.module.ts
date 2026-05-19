import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { PlateTableComponent } from "./pages/plates/plate-table/plate-table.component";
import { FooterComponent } from "./components/footer/footer.component";
import { NgOptimizedImage } from "@angular/common";
import { PlateCreateComponent } from "./pages/plates/plate-create/plate-create.component";
import { PlateEditComponent } from "./pages/plates/plate-edit/plate-edit.component";
import { PlateViewComponent } from "./pages/plates/plate-view/plate-view.component";
import { LandingPageComponent } from "./pages/landing/landing-page/landing-page.component";
import { HeroCarouselComponent } from "./pages/landing/hero-carousel/hero-carousel.component";
import { HeaderMenuComponent } from "./components/header-menu/header-menu.component";
import { ReviewCarouselComponent } from "./pages/landing/review-carousel/review-carousel.component";
import { FoodMenuGridComponent } from "./pages/landing/food-menu-grid/food-menu-grid.component";
import { FormsModule } from "@angular/forms";
import { FoodMenuGridCardComponent } from "./pages/landing/food-menu-grid-card/food-menu-grid-card.component";
import { OperatorCrudComponent } from "./pages/operators/operator-crud/operator-crud.component";
import { OperatorViewComponent } from "./pages/operators/operator-view/operator-view.component";
import { ClientCrudComponent } from "./pages/clients/client-crud/client-crud.component";
import { ClientLogInComponent } from "./pages/clients/client-log-in/client-log-in.component";
import { ClientSignUpComponent } from "./pages/clients/client-sign-up/client-sign-up.component";
import { ClientSessionComponent } from "./pages/clients/client-session/client-session.component";
import { PlateMenuComponent } from "./pages/plates/plate-menu/plate-menu.component";
import { ClientEditComponent } from "./pages/clients/client-edit/client-edit.component";
import { MakiLogoComponent } from "./components/shared/maki-logo/maki-logo.component";
import { GatewayComponent } from "./pages/admin/gateway/gateway.component";
import { ClientProfileComponent } from "./pages/clients/client-profile/client-profile.component";
import { provideHttpClient } from "@angular/common/http";
import { AdditionalCrudComponent } from "./pages/additionals/additional-crud/additional-crud.component";
import { AdditionalFormComponent } from "./pages/additionals/additional-form/additional-form.component";
import { AdditionalEditComponent } from "./pages/additionals/additional-edit/additional-edit.component";
import { OperatorFormComponent } from "./pages/operators/operator-form/operator-form.component";
import { PurchaseOrderCrudComponent } from "./pages/purchase-order/purchase-order-crud/purchase-order-crud.component";
import { FullLayoutComponent } from "./layout/full-layout/full-layout.component";
import { FooterLayoutComponent } from "./layout/footer-layout/footer-layout.component";
import { BaseLayoutComponent } from "./layout/base-layout/base-layout.component";
import { OperatorLogInComponent } from "./pages/operators/operator-log-in/operator-log-in.component";
import { AdminLogInComponent } from "./pages/admin/admin-log-in/admin-log-in.component";
import { PurchaseOrderViewComponent } from "./pages/purchase-order/purchase-order-view/purchase-order-view.component";
import { DeliveryCrudComponent } from "./pages/delivery/delivery-crud/delivery-crud.component";
import { PurchaseOrderAdminviewComponent } from "./pages/purchase-order/purchase-order-adminview/purchase-order-adminview.component";
import { AdminGatewayButtonComponent } from "./pages/admin/components/admin-gateway-button/admin-gateway-button.component";
import { ClientOrdersComponent } from "./pages/clients/client-orders/client-orders.component";
import { OperatorGatewayComponent } from "./pages/operators/operator-gateway/operator-gateway.component";
import { LogOutComponent } from "./pages/log-out/log-out.component";
import { LogInComponent } from "./pages/log-in/log-in.component";
import { AuthInterceptor } from "./service/auth.interceptor";
import { HTTP_INTERCEPTORS } from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    PlateTableComponent,
    FooterComponent,
    PlateCreateComponent,
    PlateEditComponent,
    PlateViewComponent,
    LandingPageComponent,
    HeroCarouselComponent,
    HeaderMenuComponent,
    ReviewCarouselComponent,
    OperatorCrudComponent,
    OperatorViewComponent,
    ClientCrudComponent,
    ClientLogInComponent,
    ClientSignUpComponent,
    ClientSessionComponent,
    PlateMenuComponent,
    ClientEditComponent,
    MakiLogoComponent,
    GatewayComponent,
    ClientProfileComponent,
    AdditionalCrudComponent,
    AdditionalFormComponent,
    AdditionalEditComponent,
    PurchaseOrderCrudComponent,
    FullLayoutComponent,
    FooterLayoutComponent,
    BaseLayoutComponent,
    OperatorFormComponent,
    OperatorLogInComponent,
    PurchaseOrderViewComponent,
    DeliveryCrudComponent,
    AdminLogInComponent,
    PurchaseOrderAdminviewComponent,
    AdminGatewayButtonComponent,
    ClientOrdersComponent,
    OperatorGatewayComponent,
    LogOutComponent,
    LogInComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgOptimizedImage,
    FormsModule,
    FoodMenuGridComponent,
    FoodMenuGridCardComponent,
  ],
  providers: [
    provideHttpClient(),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
