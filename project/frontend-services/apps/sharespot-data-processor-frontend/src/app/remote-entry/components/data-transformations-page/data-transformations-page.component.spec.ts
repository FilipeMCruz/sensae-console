import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataTransformationsPageComponent } from './data-transformations-page.component';

describe('SimpleAuthPageComponent', () => {
  let component: DataTransformationsPageComponent;
  let fixture: ComponentFixture<DataTransformationsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DataTransformationsPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DataTransformationsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
