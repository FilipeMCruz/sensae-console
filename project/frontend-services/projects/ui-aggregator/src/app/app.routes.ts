import {Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';

export const APP_ROUTES: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },

  // {
  //   path: '**',
  //   component: NotFoundComponent
  // }

  // DO NOT insert routes after this one.
  // { path:'**', ...} needs to be the LAST one.

];

