import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DeviceRecordPageComponent} from "./components/device-record-page/device-record-page.component";

const routes: Routes = [
  {path: '', component: DeviceRecordPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
