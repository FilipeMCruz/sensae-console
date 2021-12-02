import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DeviceRecordPageComponent } from './components/device-record-page/device-record-page.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatListModule } from '@angular/material/list';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatOptionModule } from '@angular/material/core';
import { MatRadioModule } from '@angular/material/radio';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { NewRecordEntryComponent } from './components/new-record-entry/new-record-entry.component';
import { OldDeviceRecordComponent } from './components/old-device-record/old-device-record.component';
import { NewDeviceRecordComponent } from './components/new-device-record/new-device-record.component';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    DeviceRecordPageComponent,
    NewDeviceRecordComponent,
    OldDeviceRecordComponent,
    NewRecordEntryComponent
  ],
  imports: [
    MatSidenavModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    MatListModule,
    MatSnackBarModule,
    MatExpansionModule,
    MatRadioModule,
    MatOptionModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    CommonModule,
    HttpClientModule,
    RouterModule.forChild([
      {
        path: '',
        component: DeviceRecordPageComponent
      }
    ])
  ],
  providers: []
})
export class RemoteEntryModule {
}
