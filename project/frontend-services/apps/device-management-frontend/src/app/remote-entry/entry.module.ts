import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
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
import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {MatDialogModule} from '@angular/material/dialog';
import {MatGridListModule} from "@angular/material/grid-list";
import {MatTabsModule} from "@angular/material/tabs";
import {DeviceComponent} from "./device/device.component";
import {DeviceDialogComponent} from "./device-dialog/device-dialog.component";
import {DeviceManagementPageComponent} from "./device-management-page/device-management-page.component";
import {MatChipsModule} from "@angular/material/chips";
import {MatTooltipModule} from "@angular/material/tooltip";

@NgModule({
  declarations: [
    DeviceComponent,
    DeviceDialogComponent,
    DeviceManagementPageComponent
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
                component: DeviceManagementPageComponent,
            },
        ]),
        MatGridListModule,
        MatTabsModule,
        MatChipsModule,
        MatTooltipModule,
    ],
  providers: [],
})
export class RemoteEntryModule {
}
