import { Component, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Client } from 'src/app/interfaces/client.interface';
import { ClientService } from 'src/app/service/data/client.service';

@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.css']
})
export class ClientEditComponent implements OnInit, OnDestroy, AfterViewInit {
  errorMessage: string | null = null;
  currentSlide: number = 0;
  client: Client;
  bgSlides: string[] = [
    'assets/images/carousel-login/fondo1.png',
    'assets/images/carousel-login/fondo2.png',
    'assets/images/carousel-login/fondo3.png'
  ];

  private slideInterval: ReturnType<typeof setInterval> | null = null;

  constructor(
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.client = {} as Client

    this.clientService.selectById(id).subscribe(client => {
      if(client) {
        this.client = client;
      } else {
        this.router.navigate(['/client/crud']);
      }
    })
  }

  clientUpdate(): void {
    this.clientService.update(this.client.id, this.client).subscribe(() => {
      this.router.navigate(['/client/crud']);
    })
  }

  cancelar(): void {
    this.router.navigate(['/client/crud']);
  }

  ngOnInit(): void {
    this.checkErrorParam();
  }

  ngAfterViewInit(): void {
    this.startSlideshow();
  }

  ngOnDestroy(): void {
    if (this.slideInterval) {
      clearInterval(this.slideInterval);
    }
  }

  private startSlideshow(): void {
    this.slideInterval = setInterval(() => {
      this.currentSlide = (this.currentSlide + 1) % this.bgSlides.length;
    }, 4000);
  }

  private checkErrorParam(): void {
    this.errorMessage = this.route.snapshot.queryParamMap.get('msg');
  }


  navigateTo(url: string) {
    this.router.navigate([url])
  }
}