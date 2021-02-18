import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeathItemComponent } from './death-item.component';

describe('DeathItemComponent', () => {
  let component: DeathItemComponent;
  let fixture: ComponentFixture<DeathItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeathItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeathItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
