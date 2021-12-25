import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {RouterModule} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {ToolbarComponent} from './components/toolbar/toolbar.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {LayoutModule} from '@angular/cdk/layout';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {ROUTES} from './app.routes';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {APOLLO_NAMED_OPTIONS} from "apollo-angular";
import {HttpLink} from "apollo-angular/http";
import {ApolloClientOptions, InMemoryCache, split} from "@apollo/client/core";
import {environment} from "../environments/environment";
import {HttpClientModule} from "@angular/common/http";
import {WebSocketLink} from "@apollo/client/link/ws";
import {getMainDefinition} from "@apollo/client/utilities";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatMenuModule} from '@angular/material/menu';

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
    deviceRecords: {
      link: httpLink.create({
        uri: environment.endpoints.deviceRecords.backendURL.http
      }),
      cache: new InMemoryCache()
    },
    dataProcessor: {
      link: httpLink.create({
        uri: environment.endpoints.dataProcessor.backendURL.http
      }),
      cache: new InMemoryCache(),
    },
    locationTracking: {
      link: createLinkWithWebsocket(httpLink, environment.endpoints.locationTracking.backendURL.websocket, environment.endpoints.locationTracking.backendURL.http),
      cache: new InMemoryCache(),
    }
  };
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NotFoundComponent,
    ToolbarComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatTooltipModule,
    MatToolbarModule,
    MatMenuModule,
    LayoutModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    HttpClientModule,
    RouterModule.forRoot(ROUTES, {initialNavigation: 'enabledBlocking'})
  ],
  providers: [
    {
      provide: APOLLO_NAMED_OPTIONS,
      useFactory: createNamedApollo,
      deps: [HttpLink],
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
