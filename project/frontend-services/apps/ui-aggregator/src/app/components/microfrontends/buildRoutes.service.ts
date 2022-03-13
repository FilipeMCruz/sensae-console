import {Microfrontend} from './microfrontend';
import {Routes} from '@angular/router';
import {loadRemoteModule} from '@angular-architects/module-federation';
import {APP_ROUTES, OTHER_ROUTES} from '../../app.routes';
import {AuthGuardService} from "../../services/AuthGuardService";

export function buildRoutes(options: Microfrontend[]): Routes {

  const lazyRoutes: Routes = options.map(o => {
    return {
      path: o.routePath,
      loadChildren: () => loadRemoteModule(o).then(m => m[o.ngModuleName]),
      canActivate: [AuthGuardService]
    }
  });

  return [...APP_ROUTES, ...lazyRoutes, ...OTHER_ROUTES];
}
