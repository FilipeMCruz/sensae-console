import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MapComponent} from './components/map/map.component';
import {environment} from 'src/environments/environment';
import * as mapbox from 'mapbox-gl';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MapPageComponent} from './components/map-page/map-page.component';
import {APOLLO_OPTIONS} from "apollo-angular";
import {ApolloClientOptions, InMemoryCache, split} from "@apollo/client/core";
import {HttpLink} from "apollo-angular/http";
import {getMainDefinition} from "@apollo/client/utilities";
import {WebSocketLink} from "./core/WebSocketLink";

(mapbox as any).accessToken = environment.mapbox.accessToken;

// @ts-ignore
// @ts-ignore
@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    MapPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatSidenavModule
  ],
  providers: [
    {
      provide: APOLLO_OPTIONS,
      // @ts-ignore
      useFactory(httpLink: HttpLink): ApolloClientOptions {
        // Create an http link:
        const http = httpLink.create({
          uri: environment.backendURL.http,
        });

        // Create a WebSocket link:
        const ws = new WebSocketLink({
          url: environment.backendURL.websocket,
        });

        // using the ability to split links, you can send data to each link
        // depending on what kind of operation is being sent
        const link = split(
          // split based on operation type
          ({query}) => {
            // @ts-ignore
            const {kind, operation} = getMainDefinition(query);
            return (
              kind === 'OperationDefinition' && operation === 'subscription'
            );
          },
          ws,
          http,
        );

        return {
          link,
          cache: new InMemoryCache(),
          // ... options
        };
      },
      deps: [HttpLink],
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
