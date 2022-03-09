import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { LayoutModule } from '@angular/cdk/layout';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { ROUTES } from './app.routes';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {APOLLO_NAMED_OPTIONS, ApolloModule} from 'apollo-angular';
import { HttpLink } from 'apollo-angular/http';
import { ApolloClientOptions, InMemoryCache, split } from '@apollo/client/core';
import { environment } from '../environments/environment';
import { HttpClientModule } from '@angular/common/http';
import { WebSocketLink } from '@apollo/client/link/ws';
import { getMainDefinition } from '@apollo/client/utilities';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatMenuModule } from '@angular/material/menu';
import { AuthGuardService } from './services/AuthGuardService';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { msalConfig } from './auth-config';
import {
  InteractionType,
  IPublicClientApplication,
  PublicClientApplication,
} from '@azure/msal-browser';
import {
  MSAL_GUARD_CONFIG,
  MSAL_INSTANCE,
  MsalBroadcastService,
  MsalGuard,
  MsalGuardConfiguration,
  MsalRedirectComponent,
  MsalService,
} from '@azure/msal-angular';
import { MatSnackBarModule } from '@angular/material/snack-bar';

/**
 * Here we pass the configuration parameters to create an MSAL instance.
 * For more info, visit: https://github.com/AzureAD/microsoft-authentication-library-for-js/blob/dev/lib/msal-angular/docs/v2-docs/configuration.md
 */
export function MSALInstanceFactory(): IPublicClientApplication {
  return new PublicClientApplication(msalConfig);
}

/**
 * Set your default interaction type for MSALGuard here. If you have any
 * additional scopes you want the user to consent upon login, add them here as well.
 */
export function MSALGuardConfigFactory(): MsalGuardConfiguration {
  return {
    interactionType: InteractionType.Redirect,
  };
}

export function createLinkWithWebsocket(
  httpLink: HttpLink,
  wsUrl: string,
  httpUrl: string
) {
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
    ({ query }) => {
      // @ts-ignore
      const { kind, operation } = getMainDefinition(query);
      return kind === 'OperationDefinition' && operation === 'subscription';
    },
    ws,
    http
  );
}

export function createNamedApollo(
  httpLink: HttpLink
): Record<string, ApolloClientOptions<any>> {
  return {
    deviceRecords: {
      link: httpLink.create({
        uri: environment.endpoints.deviceRecords.backendURL.http,
      }),
      cache: new InMemoryCache(),
    },
    dataProcessor: {
      link: httpLink.create({
        uri: environment.endpoints.dataProcessor.backendURL.http,
      }),
      cache: new InMemoryCache(),
    },
    identity: {
      link: httpLink.create({
        uri: environment.endpoints.identity.backendURL.http,
      }),
      cache: new InMemoryCache(),
    },
    fleetManagement: {
      link: createLinkWithWebsocket(
        httpLink,
        environment.endpoints.fleetManagement.backendURL.websocket,
        environment.endpoints.fleetManagement.backendURL.http
      ),
      cache: new InMemoryCache(),
    },
  };
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NotFoundComponent,
    ToolbarComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatTooltipModule,
    MatToolbarModule,
    MatMenuModule,
    LayoutModule,
    MatSnackBarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    HttpClientModule,
    ApolloModule,
    RouterModule.forRoot(ROUTES, { initialNavigation: 'enabledBlocking' }),
  ],
  providers: [
    {
      provide: MSAL_INSTANCE,
      useFactory: MSALInstanceFactory,
    },
    {
      provide: MSAL_GUARD_CONFIG,
      useFactory: MSALGuardConfigFactory,
    },
    MsalService,
    MsalGuard,
    MsalBroadcastService,
    {
      provide: APOLLO_NAMED_OPTIONS,
      useFactory: createNamedApollo,
      deps: [HttpLink],
    },
    AuthGuardService,
    AuthService,
  ],
  bootstrap: [AppComponent, MsalRedirectComponent],
})
export class AppModule {}
