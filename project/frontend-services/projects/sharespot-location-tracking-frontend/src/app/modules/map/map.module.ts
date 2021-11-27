import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MAP_ROUTES} from "./map.routes";
import {RouterModule} from "@angular/router";
import {MapComponent} from "./components/map/map.component";
import {SearchCardComponent} from "./components/search-card/search-card.component";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {FormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  declarations: [
    MapComponent,
    SearchCardComponent
  ],
  imports: [
    MatSidenavModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    CommonModule,
    RouterModule.forChild(MAP_ROUTES)
  ],
  providers: [],
})
export class MapModule {
}
