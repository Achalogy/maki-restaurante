import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientCrudComponent } from './client-crud.component';

describe('ClientCrudComponent', () => {
  let component: ClientCrudComponent;
  let fixture: ComponentFixture<ClientCrudComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClientCrudComponent]
    });
    fixture = TestBed.createComponent(ClientCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
