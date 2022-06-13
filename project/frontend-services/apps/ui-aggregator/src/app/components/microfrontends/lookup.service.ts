import {Injectable} from '@angular/core';
import {Microfrontend, MicrofrontendType} from './microfrontend';
import {environment} from '../../../environments/environment';

const SHARESPOT_IDENTITY_MANAGEMENT_FRONTEND_URL = environment.production
  ? 'https://' +
  environment.domain +
  '/micro-frontends/identity-management-frontend/remoteEntry.js'
  : 'http://localhost:4285/remoteEntry.js';

const SHARESPOT_FLEET_MANAGEMENT_FRONTEND_URL = environment.production
  ? 'https://' +
  environment.domain +
  '/micro-frontends/fleet-management-frontend/remoteEntry.js'
  : 'http://localhost:4284/remoteEntry.js';

const DEVICE_MANAGEMENT_FRONTEND_URL = environment.production
  ? 'https://' +
  environment.domain +
  '/micro-frontends/device-management-frontend/remoteEntry.js'
  : 'http://localhost:4283/remoteEntry.js';

const SHARESPOT_DATA_PROCESSOR_FRONTEND_URL = environment.production
  ? 'https://' +
  environment.domain +
  '/micro-frontends/data-processor-frontend/remoteEntry.js'
  : 'http://localhost:4282/remoteEntry.js';

const SHARESPOT_DATA_DECODER_FRONTEND_URL = environment.production
  ? 'https://' +
  environment.domain +
  '/micro-frontends/data-decoder-frontend/remoteEntry.js'
  : 'http://localhost:4286/remoteEntry.js';

const SMART_IRRIGATION_FRONTEND_URL = environment.production
  ? 'https://' +
  environment.domain +
  '/micro-frontends/smart-irrigation-frontend/remoteEntry.js'
  : 'http://localhost:4298/remoteEntry.js';

const RULE_MANAGEMENT_FRONTEND_URL = environment.production
  ? 'https://' +
  environment.domain +
  '/micro-frontends/rule-management-frontend/remoteEntry.js'
  : 'http://localhost:4294/remoteEntry.js';

const NOTIFICATION_MANAGEMENT_FRONTEND_URL = environment.production
  ? 'https://' +
  environment.domain +
  '/micro-frontends/notification-management-frontend/remoteEntry.js'
  : 'http://localhost:4296/remoteEntry.js';


@Injectable({providedIn: 'root'})
export class LookupService {
  lookup(): Promise<Microfrontend[]> {
    return Promise.resolve([
      {
        // For Loading
        remoteEntry: SHARESPOT_FLEET_MANAGEMENT_FRONTEND_URL,
        exposedModule: './Module',
        type: 'module',

        // For Routing
        displayName: 'Fleet Management',
        routePath: 'fleet-management',
        ngModuleName: 'RemoteEntryModule',
        icon: 'local_shipping',
        details: {
          type: MicrofrontendType.SERVICE,
          permissions: Array.of('fleet_management:latest_data:read'),
        },
      },
      {
        // For Loading
        remoteEntry: SMART_IRRIGATION_FRONTEND_URL,
        exposedModule: './Module',
        type: 'module',

        // For Routing
        displayName: 'Smart Irrigation',
        routePath: 'smart-irrigation',
        ngModuleName: 'RemoteEntryModule',
        icon: 'compost',
        details: {
          type: MicrofrontendType.SERVICE,
          permissions: Array.of('smart_irrigation:latest_data:read', "smart_irrigation:garden:read"),
        },
      },
      {
        // For Loading
        remoteEntry: DEVICE_MANAGEMENT_FRONTEND_URL,
        exposedModule: './Module',
        type: 'module',

        // For Routing
        displayName: 'Device Management',
        routePath: 'device-management',
        ngModuleName: 'RemoteEntryModule',
        icon: 'sensors',
        details: {
          type: MicrofrontendType.TOOL,
          permissions: Array.of('device_management:device:read'),
        },
      },
      {
        // For Loading
        remoteEntry: SHARESPOT_DATA_PROCESSOR_FRONTEND_URL,
        exposedModule: './Module',
        type: 'module',

        // For Routing
        displayName: 'Data Processor',
        routePath: 'transformations',
        ngModuleName: 'RemoteEntryModule',
        icon: 'transform',
        details: {
          type: MicrofrontendType.TOOL,
          permissions: Array.of('data_transformations:transformations:read'),
        },
      },
      {
        // For Loading
        remoteEntry: SHARESPOT_DATA_DECODER_FRONTEND_URL,
        exposedModule: './Module',
        type: 'module',

        // For Routing
        displayName: 'Data Decoder',
        routePath: 'decoders',
        ngModuleName: 'RemoteEntryModule',
        icon: 'data_object',
        details: {
          type: MicrofrontendType.TOOL,
          permissions: Array.of('data_decoders:decoders:read'),
        },
      },
      {
        // For Loading
        remoteEntry: SHARESPOT_IDENTITY_MANAGEMENT_FRONTEND_URL,
        exposedModule: './Module',
        type: 'module',

        // For Routing
        displayName: 'Identity Management',
        routePath: 'identity-management',
        ngModuleName: 'RemoteEntryModule',
        icon: 'domain',
        details: {
          type: MicrofrontendType.TOOL,
          permissions: Array.of(
            'identity_management:domains:read',
            'identity_management:tenant:read',
            'identity_management:device:read'
          ),
        },
      },
      {
        // For Loading
        remoteEntry: RULE_MANAGEMENT_FRONTEND_URL,
        exposedModule: './Module',
        type: 'module',

        // For Routing
        displayName: 'Rule Management',
        routePath: 'rule-management',
        ngModuleName: 'RemoteEntryModule',
        icon: 'rule',
        details: {
          type: MicrofrontendType.TOOL,
          permissions: Array.of(
            'rule_management:rules:read',
          ),
        },
      },
      {
        // For Loading
        remoteEntry: NOTIFICATION_MANAGEMENT_FRONTEND_URL,
        exposedModule: './Module',
        type: 'module',

        // For Routing
        displayName: 'Notification Management',
        routePath: 'notification-management',
        ngModuleName: 'RemoteEntryModule',
        icon: 'notifications',
        details: {
          type: MicrofrontendType.SERVICE,
          permissions: Array.of(
            'notification_management:past_data:read',
          ),
        },
      },
    ] as Microfrontend[]);
  }
}
