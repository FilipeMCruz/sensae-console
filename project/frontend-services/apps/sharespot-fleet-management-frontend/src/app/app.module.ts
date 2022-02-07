import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {APOLLO_NAMED_OPTIONS} from "apollo-angular";
import {HttpLink} from "apollo-angular/http";
import {ApolloClientOptions, InMemoryCache, split} from "@apollo/client/core";
import {WebSocketLink} from "@apollo/client/link/ws";
import {getMainDefinition} from "@apollo/client/utilities";
import {environment} from "../environments/environment";
import {RouterModule} from "@angular/router";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RemoteEntryModule} from "./remote-entry/entry.module";

export function createLinkWithWebsocket(httpLink: HttpLink, wsUrl: string, httpUrl: string) {
  const http = httpLink.create({
    uri: httpUrl,
  });

  // Create a WebSocket link:
  const ws = new WebSocketLink({
    uri: wsUrl,
    options: {
      reconnect: true,
      timeout: 30000,
    },
  });

  // using the ability to split links, you can send data to each link
  // depending on what kind of operation is being sent
  return split(
    // split based on operation type
    ({query}) => {
      // @ts-ignore
      const {kind, operation} = getMainDefinition(query);
      return (kind === 'OperationDefinition' && operation === 'subscription');
    },
    ws,
    http,
  );
}

export function createNamedApollo(httpLink: HttpLink): Record<string, ApolloClientOptions<any>> {
  return {
    fleetManagement: {
      link: createLinkWithWebsocket(httpLink, environment.backendURL.websocket, environment.backendURL.http),
      cache: new InMemoryCache(),
    }
  };
};

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    RemoteEntryModule,
    BrowserAnimationsModule,
    RouterModule.forRoot([], {initialNavigation: 'enabledBlocking'}),
  ],
  providers: [{
    provide: APOLLO_NAMED_OPTIONS,
    useFactory: createNamedApollo,
    deps: [HttpLink],
  }],
  bootstrap: [AppComponent],
})
export class AppModule {
}
