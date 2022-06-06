import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {Microfrontend, MicrofrontendType,} from '../microfrontends/microfrontend';
import {Router} from '@angular/router';
import {LookupService} from '../microfrontends/lookup.service';
import {buildRoutes} from '../microfrontends/buildRoutes.service';
import {AuthGuardService} from '../../services/AuthGuardService';
import {MSAL_GUARD_CONFIG, MsalBroadcastService, MsalGuardConfiguration, MsalService,} from '@azure/msal-angular';
import {Subject, Subscription} from 'rxjs';
import {filter, takeUntil} from 'rxjs/operators';
import {
  AuthenticationResult,
  EventMessage,
  EventType,
  InteractionStatus,
  InteractionType,
  PopupRequest,
  RedirectRequest,
} from '@azure/msal-browser';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {SnackbarService} from "../../services/SnackBarService";
import {NotificationService} from "@frontend-services/mutual";
import {Notification, NotificationSeverityLevel} from "@frontend-services/notification-management/model";
import {GoogleApiService} from "../../services/GoogleApiService";

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

  private notificationSubscription!: Subscription;

  constructor(
    @Inject(MSAL_GUARD_CONFIG) private msalGuardConfig: MsalGuardConfiguration,
    private _snackBar: SnackbarService,
    private externalAuthService: MsalService,
    private googleLogIn: GoogleApiService,
    private notifications: NotificationService,
    private authService: AuthService,
    private msalBroadcastService: MsalBroadcastService,
    private router: Router,
    private lookupService: LookupService,
    private authGuardService: AuthGuardService
  ) {
  }

  ngOnInit(): void {
    this.isIframe = window !== window.parent && !window.opener;
    this.delay(500).then(() => {
      if (this.googleLogIn.isAuthenticated()) {
        this.logInInternallyWithGoogle();
      } else {
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
            this.loginDisplay = this.authService.isAuthenticated();
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
    });
  }

  async subscribeToNotifications() {
    await this.delay(500);
    this.notificationSubscription = this.notifications.getCurrentData()
      .pipe(filter(next => !next.isEmpty()))
      .subscribe(next => this.sendNotification(next));
  }

  sendNotification(notification: Notification) {
    if (notification.contentType.severity === NotificationSeverityLevel.CRITICAL) {
      this._snackBar.critical(notification.toSnackBar());
    } else if (notification.contentType.severity === NotificationSeverityLevel.WARNING) {
      this._snackBar.warning(notification.toSnackBar());
    } else if (notification.contentType.severity === NotificationSeverityLevel.ADVISORY) {
      this._snackBar.advisory(notification.toSnackBar());
    } else if (notification.contentType.severity === NotificationSeverityLevel.WATCH) {
      this._snackBar.watch(notification.toSnackBar());
    } else {
      this._snackBar.information(notification.toSnackBar());
    }
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
        .acquireTokenSilent({scopes: ['profile']})
        .then((token) =>
          this.authService.login(token.idToken, 'Microsoft').subscribe((value) => {
            value
              ? this._snackBar.default('Valid Credentials')
              : this._snackBar.default('Invalid Credentials');
            this.subscribeToNotifications();
            this.loginDisplay = this.authService.isAuthenticated();
          })
        );
    }
  }

  async goTo(url: string) {
    await this.router.navigate(['loading']);
    await this.delay(500);
    await this.router.navigate([url]);
  }

  delay(milliseconds: number) {
    return new Promise(resolve => {
      setTimeout(resolve, milliseconds);
    });
  }

  loginWithMicrosoft() {
    if (this.msalGuardConfig.interactionType === InteractionType.Popup) {
      if (this.msalGuardConfig.authRequest) {
        this.externalAuthService
          .loginPopup({...this.msalGuardConfig.authRequest} as PopupRequest)
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
    if (this.authService.isProviderMicrosoft()) this.externalAuthService.logout();
    if (this.authService.isProviderGoogle()) this.googleLogIn.instance.revokeTokenAndLogout();
    this.authService.logout();
    if (this.notificationSubscription) this.notificationSubscription.unsubscribe();
    this.loginDisplay = this.authService.isAuthenticated();
    this.router.navigate(['']);
  }

  // unsubscribe to events when component is destroyed
  ngOnDestroy(): void {
    this.notificationSubscription.unsubscribe();
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

  profile() {
    this.router.navigate(['profile']);
  }

  logInAsAnonymous() {
    this.authService.anonymous().subscribe((value) => {
      value ? this._snackBar.default('Valid Credentials')
        : this._snackBar.default('Invalid Credentials');
      this.subscribeToNotifications();
      this.loginDisplay = this.authService.isAuthenticated();
    });
  }

  logInWithGoogle() {
    if (this.googleLogIn.isAuthenticated()) {
      this.logInInternallyWithGoogle();
    } else {
      this.checkAndSetActiveGoogleAccount();
    }
  }

  checkAndSetActiveGoogleAccount() {
    this.googleLogIn.instance.loadDiscoveryDocumentAndTryLogin().then(() => {
      this.googleLogIn.instance.tryLoginCodeFlow().then(() => {
        if (this.googleLogIn.instance.hasValidIdToken()) {
          this.logInInternallyWithGoogle();
        } else {
          this.googleLogIn.instance.initLoginFlow();
        }
      });
    });
  }

  logInInternallyWithGoogle() {
    const idToken = this.googleLogIn.instance.getIdToken();
    this.authService.login(idToken, 'Google').subscribe((value) => {
      value ? this._snackBar.default('Valid Credentials')
        : this._snackBar.default('Invalid Credentials');
      this.subscribeToNotifications();
      this.loginDisplay = this.authService.isAuthenticated();
    });
  }

  isAnonymous() {
    return this.authService.isAnonymous();
  }
}
