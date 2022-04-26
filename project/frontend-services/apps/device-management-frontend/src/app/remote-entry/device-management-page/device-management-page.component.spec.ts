import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceManagementPageComponent } from './device-management-page.component';

describe('DeviceRecordPageComponent', () => {
  let component: DeviceManagementPageComponent;
  let fixture: ComponentFixture<DeviceManagementPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeviceManagementPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceManagementPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
