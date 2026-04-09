import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PlateTableComponent } from './plates/plate-table/plate-table.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { FooterComponent } from './components/footer/footer.component';
import { NgOptimizedImage } from "@angular/common";
import { PlateCreateComponent } from './plates/plate-create/plate-create.component';
import { PlateEditComponent } from './plates/plate-edit/plate-edit.component';
import { PlateViewComponent } from './plates/plate-view/plate-view.component';
import { LandingPageComponent } from './landing/landing-page/landing-page.component';
import { HeroCarouselComponent } from './landing/hero-carousel/hero-carousel.component';
import { HeaderMenuComponent } from './landing/header-menu/header-menu.component';
import { ReviewCarouselComponent } from './landing/review-carousel/review-carousel.component';
import { FoodMenuGridComponent } from './landing/food-menu-grid/food-menu-grid.component';
import { FormsModule } from '@angular/forms';
import { FoodMenuGridCardComponent } from './landing/food-menu-grid-card/food-menu-grid-card.component';
import { OperatorCrudComponent } from './operators/operator-crud/operator-crud.component';
import { OperatorViewComponent } from './operators/operator-view/operator-view.component';

@NgModule({
  declarations: [
    AppComponent,
    PlateTableComponent,
    NavBarComponent,
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
  ],  
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgOptimizedImage,
    FormsModule,
    FoodMenuGridComponent,
    FoodMenuGridCardComponent
],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
