import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewDeviceRecordComponent } from './new-device-record.component';

describe('NewDeviceRecordComponent', () => {
  let component: NewDeviceRecordComponent;
  let fixture: ComponentFixture<NewDeviceRecordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewDeviceRecordComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewDeviceRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
