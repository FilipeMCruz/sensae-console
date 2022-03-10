import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceRecordComponent } from './device-record.component';

describe('NewDeviceRecordComponent', () => {
  let component: DeviceRecordComponent;
  let fixture: ComponentFixture<DeviceRecordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeviceRecordComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
