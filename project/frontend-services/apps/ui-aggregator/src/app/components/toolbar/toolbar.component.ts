import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import {
  Microfrontend,
  MicrofrontendType,
} from '../microfrontends/microfrontend';
import { Router } from '@angular/router';
import { LookupService } from '../microfrontends/lookup.service';
import { buildRoutes } from '../microfrontends/buildRoutes.service';
import { AuthGuardService } from '../../services/AuthGuardService';
import {
  MSAL_GUARD_CONFIG,
  MsalBroadcastService,
  MsalGuardConfiguration,
  MsalService,
} from '@azure/msal-angular';
import { Subject } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';
import {
  AuthenticationResult,
  EventMessage,
  EventType,
  InteractionStatus,
  InteractionType,
  PopupRequest,
  RedirectRequest,
} from '@azure/msal-browser';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'frontend-services-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss'],
})
export class ToolbarComponent implements OnInit, OnDestroy {
  isIframe = false;
  loginDisplay = false;
  private readonly _destroying$ = new Subject<void>();
  microfrontendServices: Microfrontend[] = [];
  microfrontendTools: Microfrontend[] = [];

  constructor(
    @Inject(MSAL_GUARD_CONFIG) private msalGuardConfig: MsalGuardConfiguration,
    private _snackBar: MatSnackBar,
    private externalAuthService: MsalService,
    private authService: AuthService,
    private msalBroadcastService: MsalBroadcastService,
    private router: Router,
    private lookupService: LookupService,
    private authGuardService: AuthGuardService
  ) {}

  ngOnInit(): void {
    this.isIframe = window !== window.parent && !window.opener;

    /**
     * You can subscribe to MSAL events as shown below. For more info,
     * visit: https://github.com/AzureAD/microsoft-authentication-library-for-js/blob/dev/lib/msal-angular/docs/v2-docs/events.md
     */
    this.msalBroadcastService.inProgress$
      .pipe(
        filter(
          (status: InteractionStatus) => status === InteractionStatus.None
        ),
        takeUntil(this._destroying$)
      )
      .subscribe(() => {
        this.setLoginDisplay();
      });

    this.msalBroadcastService.msalSubject$
      .pipe(
        filter(
          (msg: EventMessage) => msg.eventType === EventType.LOGIN_SUCCESS
        ),
        takeUntil(this._destroying$)
      )
      .subscribe((result: EventMessage) => {
        const payload = result.payload as AuthenticationResult;
        this.externalAuthService.instance.setActiveAccount(payload.account);
      });

    this.msalBroadcastService.inProgress$
      .pipe(
        filter((status: InteractionStatus) => status === InteractionStatus.None)
      )
      .subscribe(() => {
        this.setLoginDisplay();
        this.checkAndSetActiveAccount();
      });
    this.lookupService.lookup().then((microfrontends) => {
      const routes = buildRoutes(microfrontends);
      this.router.resetConfig(routes);
      this.microfrontendServices = microfrontends.filter(
        (m) => m.details.type === MicrofrontendType.SERVICE
      );
      this.microfrontendTools = microfrontends.filter(
        (m) => m.details.type === MicrofrontendType.TOOL
      );
    });
  }

  checkAndSetActiveAccount() {
    /**
     * If no active account set but there are accounts signed in, sets first account to active account
     * To use active account set here, subscribe to inProgress$ first in your component
     * Note: Basic usage demonstrated. Your app may require more complicated account selection logic
     */
    const activeAccount = this.externalAuthService.instance.getActiveAccount();

    if (
      !activeAccount &&
      this.externalAuthService.instance.getAllAccounts().length > 0
    ) {
      const accounts = this.externalAuthService.instance.getAllAccounts();
      this.externalAuthService.instance.setActiveAccount(accounts[0]);
    }

    if (activeAccount) {
      this.externalAuthService.instance
        .acquireTokenSilent({ scopes: ['profile'] })
        .then((token) =>
          this.authService.login(token.idToken).subscribe((value) => {
            value
              ? this.openSnackBar('Valid Credentials')
              : this.openSnackBar('Invalid Credentials');
          })
        );
    }
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, undefined, {
      duration: 3000,
    });
  }

  setLoginDisplay() {
    this.loginDisplay =
      this.externalAuthService.instance.getAllAccounts().length > 0;
  }

  login() {
    if (this.msalGuardConfig.interactionType === InteractionType.Popup) {
      if (this.msalGuardConfig.authRequest) {
        this.externalAuthService
          .loginPopup({ ...this.msalGuardConfig.authRequest } as PopupRequest)
          .subscribe((response: AuthenticationResult) => {
            this.externalAuthService.instance.setActiveAccount(
              response.account
            );
          });
      } else {
        this.externalAuthService
          .loginPopup()
          .subscribe((response: AuthenticationResult) => {
            this.externalAuthService.instance.setActiveAccount(
              response.account
            );
          });
      }
    } else {
      if (this.msalGuardConfig.authRequest) {
        this.externalAuthService.loginRedirect({
          ...this.msalGuardConfig.authRequest,
        } as RedirectRequest);
      } else {
        this.externalAuthService.loginRedirect();
      }
    }
  }

  logout() {
    this.authService.logout();
    this.externalAuthService.logout();
  }

  // unsubscribe to events when component is destroyed
  ngOnDestroy(): void {
    this._destroying$.next(undefined);
    this._destroying$.complete();
  }

  canShow(route: Microfrontend) {
    return this.authGuardService.canShow(
      route.details.permissions,
      route.routePath
    );
  }

  canShowAny(routes: Microfrontend[]) {
    return routes.some((r) =>
      this.authGuardService.canShow(r.details.permissions, r.routePath)
    );
  }
}
