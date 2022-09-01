import {Injectable} from '@angular/core';
import {ValidateCredentials} from './services/ValidateCredentials';
import {ReplaySubject} from 'rxjs';
import jwt_decode, {JwtPayload} from 'jwt-decode';
import {TenantIdentity} from './dto/CredentialsDTO';
import {AnonymousCredentials} from "./services/AnonymousCredentials";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private payload!: TenantIdentity;

  private accessToken!: string;

  private idToken!: string;

  private provider!: string;

  constructor(private validatorService: ValidateCredentials,
              private anonymousService: AnonymousCredentials) {
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

    const exp = payload.exp ? payload.exp : new Date().getTime() + 10 * 60000;
    return {email, domains, name, permissions, oid, exp};
  }

  login(token: string, provider: string) {
    console.log("login")
    this.provider = provider;
    this.idToken = token;
    const subject = new ReplaySubject(1);
    this.validatorService.validate(token, provider).subscribe((next) => {
      const token = next.data?.authenticate.token;
      if (token) {
        this.accessToken = token;
        this.payload = AuthService.toDto(jwt_decode<JwtPayload>(this.accessToken));
        subject.next(true);
      } else {
        subject.next(false);
      }
    });
    return subject;
  }

  anonymous() {
    this.provider = "anonymous";
    const subject = new ReplaySubject(1);
    this.anonymousService.validate().subscribe((next) => {
      const token = next.data?.anonymous.token;
      if (token) {
        this.accessToken = token;
        this.payload = AuthService.toDto(jwt_decode<JwtPayload>(this.accessToken));
        subject.next(true);
      } else {
        subject.next(false);
      }
    });
    return subject;
  }

  isAnonymous() {
    return this.provider == "anonymous";
  }

  isAllowed(permissions: string[]) {
    return !permissions.some((p) => {
      return !this.payload.permissions.includes(p);
    });
  }

  logout() {
    this.accessToken = '';
  }

  isAuthenticated(): boolean {
    return this.accessToken != null && this.accessToken.trim().length > 0;
  }

  getDomains(): string[] {
    return this.payload.domains;
  }

  getToken(): string {
    if (this.payload.exp > new Date().getTime())
      this.login(this.idToken, this.provider);

    return this.accessToken ? this.accessToken : '';
  }

  isProviderMicrosoft() {
    return this.provider === "Microsoft";
  }

  isProviderGoogle() {
    return this.provider === "Google";
  }

  getOid() {
    return this.payload.oid;
  }

  getName() {
    return this.payload.name;
  }
}
