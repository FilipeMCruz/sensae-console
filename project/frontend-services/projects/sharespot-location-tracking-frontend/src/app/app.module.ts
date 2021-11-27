import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import * as mapbox from 'mapbox-gl';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {FormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {APOLLO_OPTIONS} from "apollo-angular";
import {HttpLink} from "apollo-angular/http";
import {ApolloClientOptions, InMemoryCache, split} from "@apollo/client/core";
import {WebSocketLink} from "@apollo/client/link/ws";
import {getMainDefinition} from "@apollo/client/utilities";
import {environment} from "../environments/environment";

(mapbox as any).accessToken = environment.mapbox.accessToken;

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    FormsModule,
    MatInputModule,
    MatButtonModule
  ],
  providers: [{
    provide: APOLLO_OPTIONS,
    // @ts-ignore
    useFactory(httpLink: HttpLink): ApolloClientOptions {
      // Create an http link:
      const http = httpLink.create({
        uri: environment.backendURL.http,
      });

      // Create a WebSocket link:
      const ws = new WebSocketLink({
        uri: environment.backendURL.websocket,
        options: {
          reconnect: true,
        },
      });

      // using the ability to split links, you can send data to each link
      // depending on what kind of operation is being sent
      const link = split(
        // split based on operation type
        ({query}) => {
          // @ts-ignore
          const {kind, operation} = getMainDefinition(query);
          return (kind === 'OperationDefinition' && operation === 'subscription');
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
  },],
  bootstrap: [AppComponent]
})
export class AppModule {
}
