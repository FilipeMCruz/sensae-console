import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IdentityManagementPageComponent } from './identity-management-page.component';

describe('DeviceRecordPageComponent', () => {
  let component: IdentityManagementPageComponent;
  let fixture: ComponentFixture<IdentityManagementPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [IdentityManagementPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IdentityManagementPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
