import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataDecodersPageComponent } from './data-decoders-page.component';

describe('SimpleAuthPageComponent', () => {
  let component: DataDecodersPageComponent;
  let fixture: ComponentFixture<DataDecodersPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DataDecodersPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DataDecodersPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
