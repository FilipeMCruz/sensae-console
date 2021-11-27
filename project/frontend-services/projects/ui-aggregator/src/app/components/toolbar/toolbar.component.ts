import {Component, OnInit} from '@angular/core';
import {Microfrontend} from "../microfrontends/microfrontend";
import {Router} from "@angular/router";
import {LookupService} from "../microfrontends/lookup.service";
import {buildRoutes} from "../microfrontends/buildRoutes.service";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {
  microfrontends: Microfrontend[] = [];

  constructor(
    private router: Router,
    private lookupService: LookupService) {
  }

  async ngOnInit(): Promise<void> {
    this.microfrontends = await this.lookupService.lookup();
    const routes = buildRoutes(this.microfrontends);
    this.router.resetConfig(routes);
  }
}
