import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimpleAuthPageComponent } from './simple-auth-page.component';

describe('DeviceRecordPageComponent', () => {
  let component: SimpleAuthPageComponent;
  let fixture: ComponentFixture<SimpleAuthPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SimpleAuthPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimpleAuthPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
