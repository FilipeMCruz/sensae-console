import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationManagementPageComponent } from './notification-management-page.component';

describe('SimpleAuthPageComponent', () => {
  let component: NotificationManagementPageComponent;
  let fixture: ComponentFixture<NotificationManagementPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotificationManagementPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationManagementPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
