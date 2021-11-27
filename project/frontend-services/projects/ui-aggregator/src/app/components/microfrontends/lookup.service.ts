import {Injectable} from '@angular/core';
import {Microfrontend} from './microfrontend';

const SHARESPOT_LOCATION_TRACKING_FRONTEND_URL = 'http://localhost:7085/remoteEntry.js';
const SHARESPOT_DEVICE_RECORDS_FRONTEND_URL = 'http://localhost:7084/remoteEntry.js';

@Injectable({providedIn: 'root'})
export class LookupService {
  lookup(): Promise<Microfrontend[]> {
    return Promise.resolve([
      {
        // For Loading
        remoteEntry: SHARESPOT_LOCATION_TRACKING_FRONTEND_URL,
        remoteName: 'sharespotLocationTrackingFrontend',
        exposedModule: './Module',

        // For Routing
        displayName: 'Location Tracking',
        routePath: 'location-tracking',
        ngModuleName: 'LocationTrackingModule'
      },
      {
        // For Loading
        remoteEntry: SHARESPOT_DEVICE_RECORDS_FRONTEND_URL,
        remoteName: 'sharespotDeviceRecordsFrontend',
        exposedModule: './Module',

        // For Routing
        displayName: 'Device Records',
        routePath: 'records',
        ngModuleName: 'DeviceRecordsModule'
      }
    ] as Microfrontend[]);
  }
}
