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
    FoodMenuGridComponent
  ],  
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgOptimizedImage,
    FormsModule
],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
