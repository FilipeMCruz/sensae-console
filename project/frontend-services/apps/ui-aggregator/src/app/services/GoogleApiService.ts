import {Injectable} from "@angular/core";
import {AuthConfig, OAuthService} from "angular-oauth2-oidc";
import {environment} from "../../environments/environment";

const oAuthConfig: AuthConfig = {
  issuer: 'https://accounts.google.com',
  strictDiscoveryDocumentValidation: false,
  redirectUri: window.location.origin,
  clientId: environment.auth.google.clientId,
  scope: 'openid profile email',
  postLogoutRedirectUri: window.location.origin,
}

@Injectable({
  providedIn: 'root'
})
export class GoogleApiService {


  constructor(public readonly instance: OAuthService) {
    instance.configure(oAuthConfig);
    instance.loadDiscoveryDocumentAndTryLogin();
  }

  isAuthenticated() {
    return this.instance.hasValidIdToken();
  }
}
