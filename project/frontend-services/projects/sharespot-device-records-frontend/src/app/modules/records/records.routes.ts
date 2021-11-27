import {Routes} from "@angular/router";
import {DeviceRecordPageComponent} from "./components/device-record-page/device-record-page.component";

export const RECORDS_ROUTES: Routes = [
  {
    path: '',
    redirectTo: 'device-search'
  },
  {
    path: 'device-search',
    component: DeviceRecordPageComponent
  }
];
