import {Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {LoadingPageComponent} from "./components/loading-page/loading-page.component";

export const APP_ROUTES: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full',
  },
  {
    path: 'loading',
    component: LoadingPageComponent,
    pathMatch: 'full',
  },
];

export const OTHER_ROUTES: Routes = [
  {
    path: '**',
    component: NotFoundComponent,
  },
];

export const ROUTES: Routes = [...APP_ROUTES, ...OTHER_ROUTES];
