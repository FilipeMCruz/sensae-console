import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {SimpleAuthPageComponent} from "./components/simple-auth-page/simple-auth-page.component";
import {MatStepperModule} from "@angular/material/stepper";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatCardModule} from "@angular/material/card";
import {MatDialogModule} from "@angular/material/dialog";
import {MatSelectModule} from "@angular/material/select";
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
import {CommonModule} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [SimpleAuthPageComponent],
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
        component: SimpleAuthPageComponent,
      },
    ]),
  ],
  providers: [],
})
export class RemoteEntryModule {
}
