import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {DeviceRecordPageComponent} from './device-record-page/device-record-page.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatListModule} from '@angular/material/list';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatOptionModule} from '@angular/material/core';
import {MatRadioModule} from '@angular/material/radio';
import {MatInputModule} from '@angular/material/input';
import {MatStepperModule} from '@angular/material/stepper';
import {MatButtonModule} from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import {DeviceRecordComponent} from './device-record/device-record.component';
import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {DeviceRecordDialogComponent} from './device-record-dialog/device-record-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatGridListModule} from "@angular/material/grid-list";
import {MatTabsModule} from "@angular/material/tabs";

@NgModule({
  declarations: [
    DeviceRecordPageComponent,
    DeviceRecordComponent,
    DeviceRecordDialogComponent,
  ],
  imports: [
    MatStepperModule,
    MatSidenavModule,
    MatCardModule,
    MatDialogModule,
    MatSelectModule,
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
        component: DeviceRecordPageComponent,
      },
    ]),
    MatGridListModule,
    MatTabsModule,
  ],
  providers: [],
})
export class RemoteEntryModule {
}
