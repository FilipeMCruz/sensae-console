import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DeviceRecordPageComponent} from "./components/device-record-page/device-record-page.component";
import {NewDeviceRecordComponent} from "./components/new-device-record/new-device-record.component";
import {OldDeviceRecordComponent} from "./components/old-device-record/old-device-record.component";
import {NewRecordEntryComponent} from "./components/new-record-entry/new-record-entry.component";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatRadioModule} from "@angular/material/radio";
import {MatOptionModule} from "@angular/material/core";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {RouterModule} from "@angular/router";
import {RECORDS_ROUTES} from './records.routes';

@NgModule({
  declarations: [
    DeviceRecordPageComponent,
    NewDeviceRecordComponent,
    OldDeviceRecordComponent,
    NewRecordEntryComponent
  ],
  imports: [
    RouterModule.forChild(RECORDS_ROUTES),
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
    CommonModule
  ]
})
export class RecordsModule {
}
