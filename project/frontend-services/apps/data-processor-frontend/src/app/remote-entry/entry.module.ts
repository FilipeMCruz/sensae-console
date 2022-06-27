import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatStepperModule } from '@angular/material/stepper';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatRadioModule } from '@angular/material/radio';
import { MatOptionModule } from '@angular/material/core';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { DataTransformationsPageComponent } from './data-transformations-page/data-transformations-page.component';
import { DataTransformationComponent } from './data-transformation/data-transformation.component';
import { DataTransformationDialogComponent } from './data-transformation-dialog/data-transformation-dialog.component';
import {MatTabsModule} from "@angular/material/tabs";
import {MatChipsModule} from "@angular/material/chips";
import {MatTooltipModule} from "@angular/material/tooltip";

@NgModule({
  declarations: [
    DataTransformationsPageComponent,
    DataTransformationComponent,
    DataTransformationDialogComponent,
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
        component: DataTransformationsPageComponent,
      },
    ]),
    MatTabsModule,
    MatChipsModule,
    MatTooltipModule,
  ],
  providers: [],
})
export class RemoteEntryModule {}
