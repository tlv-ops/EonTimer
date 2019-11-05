import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Gen3TimerComponent } from './gen3-timer.component';

describe('Gen3TimerComponent', () => {
  let component: Gen3TimerComponent;
  let fixture: ComponentFixture<Gen3TimerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Gen3TimerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Gen3TimerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
