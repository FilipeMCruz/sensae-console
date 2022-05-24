/*
 * This RemoteEntryModule is imported here to allow TS to find the Module during
 * compilation, allowing it to be included in the built bundle. This is required
 * for the Module Federation Plugin to expose the Module correctly.
 * */
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {RouterModule} from '@angular/router';
import {HttpLink} from "apollo-angular/http";
import {ApolloClientOptions, InMemoryCache} from "@apollo/client/core";
import {createLink} from "@frontend-services/mutual";
import {environment} from "../environments/environment";
import {APOLLO_NAMED_OPTIONS, ApolloModule} from "apollo-angular";
import {RemoteEntryModule} from "../../../sharespot-data-decoder-frontend/src/app/remote-entry/entry.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

export function createNamedApollo(
  httpLink: HttpLink
): Record<string, ApolloClientOptions<any>> {
  return {
    ruleManagement: {
      link: createLink(httpLink, environment.endpoints.ruleManagement.backend),
      cache: new InMemoryCache(),
    },
  };
}

@NgModule({
  declarations: [AppComponent],
  imports: [
    ApolloModule,
    BrowserModule,
    RemoteEntryModule,
    BrowserAnimationsModule,
    RouterModule.forRoot([], {initialNavigation: 'enabledBlocking'}),
  ],
  providers: [
    {
      provide: APOLLO_NAMED_OPTIONS,
      useFactory: createNamedApollo,
      deps: [HttpLink],
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
