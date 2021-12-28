import {Injectable} from '@angular/core';
import {Microfrontend, MicrofrontendType} from './microfrontend';
import {environment} from "../../../environments/environment";

const SHARESPOT_LOCATION_TRACKING_FRONTEND_URL = environment.production ? 'https://localhost/micro-frontends/sharespot-location-tracking-frontend/remoteEntry.js' : 'http://localhost:4284/remoteEntry.js';
const SHARESPOT_DEVICE_RECORDS_FRONTEND_URL = environment.production ? 'https://localhost/micro-frontends/sharespot-device-records-frontend/remoteEntry.js' : 'http://localhost:4283/remoteEntry.js';
const SHARESPOT_DATA_PROCESSOR_FRONTEND_URL = environment.production ? 'https://localhost/micro-frontends/sharespot-data-processor-frontend/remoteEntry.js' : 'http://localhost:4282/remoteEntry.js';

@Injectable({providedIn: 'root'})
export class LookupService {
  lookup(): Promise<Microfrontend[]> {
    return Promise.resolve([
      {
        // For Loading
        remoteEntry: SHARESPOT_LOCATION_TRACKING_FRONTEND_URL,
        remoteName: 'sharespotlocationtrackingfrontend',
        exposedModule: './Module',

        // For Routing
        displayName: 'Location Tracking',
        routePath: 'location-tracking',
        ngModuleName: 'RemoteEntryModule',
        details: {
          type: MicrofrontendType.SERVICE
        }
      },
      {
        // For Loading
        remoteEntry: SHARESPOT_DEVICE_RECORDS_FRONTEND_URL,
        remoteName: 'sharespotdevicerecordsfrontend',
        exposedModule: './Module',

        // For Routing
        displayName: 'Device Records',
        routePath: 'records',
        ngModuleName: 'RemoteEntryModule',
        details: {
          type: MicrofrontendType.TOOL
        }
      },
      {
        // For Loading
        remoteEntry: SHARESPOT_DATA_PROCESSOR_FRONTEND_URL,
        remoteName: 'sharespotdataprocessorfrontend',
        exposedModule: './Module',

        // For Routing
        displayName: 'Data Processor',
        routePath: 'transformations',
        ngModuleName: 'RemoteEntryModule',
        details: {
          type: MicrofrontendType.TOOL
        }
      }
    ] as Microfrontend[]);
  }
}
