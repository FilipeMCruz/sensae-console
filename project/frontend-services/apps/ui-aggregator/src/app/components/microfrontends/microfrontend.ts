import {LoadRemoteModuleOptions} from '@angular-architects/module-federation';

export type Microfrontend = LoadRemoteModuleOptions & {
  displayName: string;
  routePath: string;
  ngModuleName: string;
  type: string;
  details: MicrofrontendDetails;
  icon: string;
};

export type MicrofrontendDetails = {
  type: MicrofrontendType;
  permissions: string[];
};

export enum MicrofrontendType {
  TOOL = 0,
  SERVICE = 1,
}
