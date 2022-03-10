import { NgModule } from '@angular/core';
import * as mapbox from 'mapbox-gl';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MapComponent } from './map/map.component';
import { SearchCardComponent } from './search-card/search-card.component';
import { environment } from '../../environments/environment';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatDividerModule } from '@angular/material/divider';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTabsModule } from '@angular/material/tabs';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatSliderModule } from '@angular/material/slider';

(mapbox as any).accessToken = environment.mapbox.accessToken;

@NgModule({
  declarations: [MapComponent, SearchCardComponent],
  imports: [
    MatSliderModule,
    MatSlideToggleModule,
    MatDividerModule,
    MatTooltipModule,
    MatSelectModule,
    MatSidenavModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCheckboxModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    FormsModule,
    MatExpansionModule,
    MatInputModule,
    MatButtonModule,
    HttpClientModule,
    CommonModule,
    MatTabsModule,
    MatButtonToggleModule,
    RouterModule.forChild([
      {
        path: '',
        component: MapComponent,
      },
    ]),
  ],
  providers: [],
})
export class RemoteEntryModule {}
