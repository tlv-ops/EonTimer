import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Gen4TimerComponent } from './gen4-timer.component';

describe('Gen4TimerComponent', () => {
  let component: Gen4TimerComponent;
  let fixture: ComponentFixture<Gen4TimerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Gen4TimerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Gen4TimerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
