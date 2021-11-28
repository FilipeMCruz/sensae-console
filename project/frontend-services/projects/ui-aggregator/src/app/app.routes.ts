import {Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {loadRemoteModule} from "@angular-architects/module-federation";

export const APP_ROUTES: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },

  {
    path: 'records',
    loadChildren: () => loadRemoteModule({
      remoteEntry: "http://localhost:7084/remoteEntry.js",
      remoteName: 'sharespotDeviceRecordsFrontend',
      exposedModule: './Module'
    })
      .then(m => m.RecordsModule)
  },
  // {
  //   path: '**',
  //   component: NotFoundComponent
  // }

  // DO NOT insert routes after this one.
  // { path:'**', ...} needs to be the LAST one.

];

