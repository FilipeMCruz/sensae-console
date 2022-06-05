import {Injectable} from '@angular/core';
import {ValidateCredentials} from './services/ValidateCredentials';
import {ReplaySubject} from 'rxjs';
import jwt_decode, {JwtPayload} from 'jwt-decode';
import {TenantIdentity} from './dto/CredentialsDTO';
import {RefreshAuthToken} from "./services/RefreshAuthToken";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private payload?: TenantIdentity;

  private accessToken!: string;

  private provider!: string;

  constructor(private validator: ValidateCredentials,
              private refresher: RefreshAuthToken) {
  }

  private static toDto(payload: JwtPayload): TenantIdentity {
    // @ts-ignore
    const email = payload['email'];
    // @ts-ignore
    const name = payload['name'];
    // @ts-ignore
    const oid = payload['oid'];
    // @ts-ignore
    const domains = payload['domains'];
    // @ts-ignore
    const permissions = payload['permissions'];
    return {email, domains, name, permissions, oid};
  }

  login(token: string, provider: string) {
    this.provider = provider;
    const subject = new ReplaySubject(1);
    this.validator.validate(token, provider).subscribe((next) => {
      const token = next.data?.authenticate.token;
      if (token) {
        this.accessToken = token;
        this.payload = AuthService.toDto(jwt_decode<JwtPayload>(this.accessToken));
        this.startAuthTokenPooling();
        subject.next(true);
      } else {
        subject.next(false);
      }
    });
    return subject;
  }

  isAllowed(permissions: string[]) {
    return !permissions.some((p) => {
      return !this.payload?.permissions.includes(p);
    });
  }

  startAuthTokenPooling() {
    setInterval(() => this.refresh(this.accessToken), 1500000);
  }

  refresh(token: string) {
    this.refresher.refresh(token).subscribe((next) => {
      const token = next.data?.refresh.token;
      if (token) {
        this.accessToken = token;
        this.payload = AuthService.toDto(jwt_decode<JwtPayload>(this.accessToken));
      }
    });
  }

  logout() {
    this.accessToken = "";
  }

  isAuthenticated(): boolean {
    return this.accessToken != null && this.accessToken.trim().length > 0;
  }

  getDomains(): string[] {
    if (this.payload) {
      return this.payload.domains;
    }
    return [];
  }

  getToken(): string {
    return this.accessToken ? this.accessToken : '';
  }

  isProviderMicrosoft() {
    return this.provider === "Microsoft";
  }

  isProviderGoogle() {
    return this.provider === "Google";
  }
}
