import {Component, OnInit} from '@angular/core';
import {Microfrontend, MicrofrontendType} from '../microfrontends/microfrontend';
import {Router} from '@angular/router';
import {LookupService} from '../microfrontends/lookup.service';
import {buildRoutes} from '../microfrontends/buildRoutes.service';
import {AuthGuardService} from "../../services/AuthGuardService";

@Component({
  selector: 'frontend-services-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  microfrontendServices: Microfrontend[] = [];
  microfrontendTools: Microfrontend[] = [];

  constructor(
    private router: Router,
    private lookupService: LookupService,
    private authGuardService: AuthGuardService) {
  }

  async ngOnInit(): Promise<void> {
    const microfrontends = await this.lookupService.lookup();
    const routes = buildRoutes(microfrontends);
    this.router.resetConfig(routes);
    this.microfrontendServices = microfrontends.filter(m => m.details.type === MicrofrontendType.SERVICE);
    this.microfrontendTools = microfrontends.filter(m => m.details.type === MicrofrontendType.TOOL);
  }

  canShow(route: string) {
    return this.authGuardService.canShow(route);
  }

  canShowAny(routes: Microfrontend[]) {
    return routes.some(r => this.authGuardService.canShow(r.routePath))
  }
}
