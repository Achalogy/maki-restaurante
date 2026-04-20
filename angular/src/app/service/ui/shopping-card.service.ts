import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  // Emite eventos :D
  private isOpen$ = new BehaviorSubject<boolean>(false);

  toggle() {
    this.isOpen$.next(!this.isOpen$.value);
  }

  open() {
    this.isOpen$.next(true);
  }

  close() {
    this.isOpen$.next(false);
  }

  get state() {
    return this.isOpen$.asObservable();
  }

}