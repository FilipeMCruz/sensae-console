import { Injectable } from '@angular/core';
import { Microfrontend, MicrofrontendType } from './microfrontend';
import { environment } from '../../../environments/environment';

const SHARESPOT_FLEET_MANAGEMENT_FRONTEND_URL = environment.production
  ? 'https://' +
    environment.domain +
    '/micro-frontends/sharespot-fleet-management-frontend/remoteEntry.js'
  : 'http://localhost:4284/remoteEntry.js';
const SHARESPOT_DEVICE_RECORDS_FRONTEND_URL = environment.production
  ? 'https://' +
    environment.domain +
    '/micro-frontends/sharespot-device-records-frontend/remoteEntry.js'
  : 'http://localhost:4283/remoteEntry.js';
const SHARESPOT_DATA_PROCESSOR_FRONTEND_URL = environment.production
  ? 'https://' +
    environment.domain +
    '/micro-frontends/sharespot-data-processor-frontend/remoteEntry.js'
  : 'http://localhost:4282/remoteEntry.js';
const SHARESPOT_SIMPLE_AUTH_FRONTEND_URL = environment.production
  ? 'https://' +
    environment.domain +
    '/micro-frontends/sharespot-simple-auth-frontend/remoteEntry.js'
  : 'http://localhost:4290/remoteEntry.js';

@Injectable({ providedIn: 'root' })
export class LookupService {
  lookup(): Promise<Microfrontend[]> {
    return Promise.resolve([
      {
        // For Loading
        remoteEntry: SHARESPOT_FLEET_MANAGEMENT_FRONTEND_URL,
        remoteName: 'sharespotfleetmanagementfrontend',
        exposedModule: './Module',

        // For Routing
        displayName: 'Fleet Management',
        routePath: 'fleet-management',
        ngModuleName: 'RemoteEntryModule',
        details: {
          type: MicrofrontendType.SERVICE,
          protected: true,
        },
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
          type: MicrofrontendType.TOOL,
          protected: true,
        },
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
          type: MicrofrontendType.TOOL,
          protected: true,
        },
      },
      {
        // For Loading
        remoteEntry: SHARESPOT_SIMPLE_AUTH_FRONTEND_URL,
        remoteName: 'sharespotsimpleauthfrontend',
        exposedModule: './Module',

        // For Routing
        displayName: 'Authentication',
        routePath: 'auth',
        ngModuleName: 'RemoteEntryModule',
        details: {
          type: MicrofrontendType.SERVICE,
          protected: false,
        },
      },
    ] as Microfrontend[]);
  }
}
