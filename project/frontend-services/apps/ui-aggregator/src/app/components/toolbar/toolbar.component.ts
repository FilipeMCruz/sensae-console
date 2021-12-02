import { Component, OnInit } from '@angular/core';
import { Microfrontend } from '../microfrontends/microfrontend';
import { Router } from '@angular/router';
import { LookupService } from '../microfrontends/lookup.service';
import { buildRoutes } from '../microfrontends/buildRoutes.service';

@Component({
  selector: 'frontend-services-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  microfrontends: Microfrontend[] = [];

  constructor(
    private router: Router,
    private lookupService: LookupService) {
  }

  async ngOnInit(): Promise<void> {
    this.microfrontends = await this.lookupService.lookup();
    console.log('MicroFrontends:', this.microfrontends);
    const routes = buildRoutes(this.microfrontends);
    this.router.resetConfig(routes);
    console.log("Config", this.router.config);
  }
}
