import {LoadRemoteModuleOptions} from '@angular-architects/module-federation';

export type Microfrontend = LoadRemoteModuleOptions & {
  displayName: string;
  routePath: string;
  ngModuleName: string;
  details: MicrofrontendDetails,
};

export type MicrofrontendDetails = {
  type: MicrofrontendType,
  protected: boolean,
  permissions: string[]
}

export enum MicrofrontendType {
  TOOL = 0,
  SERVICE = 1
}
