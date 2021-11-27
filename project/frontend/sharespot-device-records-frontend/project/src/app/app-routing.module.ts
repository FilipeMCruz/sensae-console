import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'records',
    pathMatch: 'full'
  },
  {
    path: 'records',
    loadChildren: () => import('./modules/records/records.module')
      .then(m => m.RecordsModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
