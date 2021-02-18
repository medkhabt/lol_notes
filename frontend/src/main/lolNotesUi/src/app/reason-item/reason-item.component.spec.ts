import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReasonItemComponent } from './reason-item.component';

describe('ReasonItemComponent', () => {
  let component: ReasonItemComponent;
  let fixture: ComponentFixture<ReasonItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReasonItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReasonItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
