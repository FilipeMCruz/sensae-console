import {NgModule} from '@angular/core';
import * as mapbox from 'mapbox-gl';
import {RouterModule} from '@angular/router';
import {CommonModule} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MapComponent} from "./components/map/map.component";
import {SearchCardComponent} from "./components/search-card/search-card.component";
import {environment} from "../../environments/environment";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";

(mapbox as any).accessToken = environment.mapbox.accessToken;

@NgModule({
  declarations: [
    MapComponent,
    SearchCardComponent
  ],
  imports: [
    MatTooltipModule,
    MatSelectModule,
    MatSidenavModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    HttpClientModule,
    CommonModule,
    RouterModule.forChild([
      {
        path: '',
        component: MapComponent,
      },
    ]),
  ],
  providers: [],
})
export class RemoteEntryModule {
}
