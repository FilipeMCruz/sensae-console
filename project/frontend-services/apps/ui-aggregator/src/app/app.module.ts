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
import {APOLLO_NAMED_OPTIONS, ApolloModule} from 'apollo-angular';
import {HttpLink} from 'apollo-angular/http';
import {ApolloClientOptions, InMemoryCache} from '@apollo/client/core';
import {environment} from '../environments/environment';
import {HttpClientModule} from '@angular/common/http';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatMenuModule} from '@angular/material/menu';
import {AuthGuardService} from './services/AuthGuardService';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {msalConfig} from './auth-config';
import {InteractionType, IPublicClientApplication, PublicClientApplication,} from '@azure/msal-browser';
import {
  MSAL_GUARD_CONFIG,
  MSAL_INSTANCE,
  MsalBroadcastService,
  MsalGuard,
  MsalGuardConfiguration,
  MsalRedirectComponent,
  MsalService,
} from '@azure/msal-angular';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {createLink} from '@frontend-services/mutual';
import {LoadingPageComponent} from "./components/loading-page/loading-page.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {OAuthModule} from "angular-oauth2-oidc";

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

export function createNamedApollo(
  httpLink: HttpLink
): Record<string, ApolloClientOptions<any>> {
  return {
    deviceInformation: {
      link: createLink(httpLink, environment.endpoints.deviceInformation.backend),
      cache: new InMemoryCache(),
    },
    dataProcessor: {
      link: createLink(httpLink, environment.endpoints.dataProcessor.backend),
      cache: new InMemoryCache(),
    },
    identity: {
      link: createLink(httpLink, environment.endpoints.identity.backend),
      cache: new InMemoryCache(),
    },
    fleetManagement: {
      link: createLink(httpLink, environment.endpoints.fleetManagement.backend),
      cache: new InMemoryCache(),
    },
    dataDecoder: {
      link: createLink(httpLink, environment.endpoints.dataDecoder.backend),
      cache: new InMemoryCache(),
    },
    smartIrrigation: {
      link: createLink(httpLink, environment.endpoints.smartIrrigation.backend),
      cache: new InMemoryCache(),
    },
    ruleManagement: {
      link: createLink(httpLink, environment.endpoints.ruleManagement.backend),
      cache: new InMemoryCache(),
    },
    notificationManagement: {
      link: createLink(httpLink, environment.endpoints.notificationManagement.backend),
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
    LoadingPageComponent,
    ProfileComponent,
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
    RouterModule.forRoot(ROUTES, {initialNavigation: 'enabledBlocking'}),
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    OAuthModule.forRoot()
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
export class AppModule {
}
