import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PlateTableComponent } from './plates/plate-table/plate-table.component';
import { FooterComponent } from './components/footer/footer.component';
import { NgOptimizedImage } from "@angular/common";
import { PlateCreateComponent } from './plates/plate-create/plate-create.component';
import { PlateEditComponent } from './plates/plate-edit/plate-edit.component';
import { PlateViewComponent } from './plates/plate-view/plate-view.component';
import { LandingPageComponent } from './landing/landing-page/landing-page.component';
import { HeroCarouselComponent } from './landing/hero-carousel/hero-carousel.component';
import { HeaderMenuComponent } from './components/header-menu/header-menu.component';
import { ReviewCarouselComponent } from './landing/review-carousel/review-carousel.component';
import { FoodMenuGridComponent } from './landing/food-menu-grid/food-menu-grid.component';
import { FormsModule } from '@angular/forms';
import { FoodMenuGridCardComponent } from './landing/food-menu-grid-card/food-menu-grid-card.component';
import { OperatorCrudComponent } from './operators/operator-crud/operator-crud.component';
import { OperatorViewComponent } from './operators/operator-view/operator-view.component';
import { ClientCrudComponent } from './clients/client-crud/client-crud.component';
import { ClientLogInComponent } from './clients/client-log-in/client-log-in.component';
import { ClientSignUpComponent } from './clients/client-sign-up/client-sign-up.component';
import { ClientSessionComponent } from './clients/client-session/client-session.component';
import { PlateMenuComponent } from './plates/plate-menu/plate-menu.component';
import { ClientEditComponent } from './clients/client-edit/client-edit.component';
import { MakiLogoComponent } from './components/shared/maki-logo/maki-logo.component';
import { GatewayComponent } from './admin/gateway/gateway.component';
import { ClientProfileComponent } from './clients/client-profile/client-profile.component';
import { provideHttpClient } from '@angular/common/http';
import { AditionalCrudComponent } from './aditionals/aditional-crud/aditional-crud.component';
import { AditionalFormComponent } from './aditionals/aditional-form/aditional-form.component';

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
    AditionalCrudComponent,
    AditionalFormComponent,
  ],  
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgOptimizedImage,
    FormsModule,
    FoodMenuGridComponent,
    FoodMenuGridCardComponent
],
  providers: [
    provideHttpClient()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
