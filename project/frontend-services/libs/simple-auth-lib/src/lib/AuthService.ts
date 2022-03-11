import { Injectable } from '@angular/core';
import { ValidateCredentials } from './services/ValidateCredentials';
import { ReplaySubject } from 'rxjs';
import jwt_decode, { JwtPayload } from 'jwt-decode';
import { TenantIdentity } from './dto/CredentialsDTO';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private payload?: TenantIdentity;

  private accessToken?: string;

  constructor(private validator: ValidateCredentials) {}

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
    return { email, domains, name, permissions, oid };
  }

  login(token: string) {
    const subject = new ReplaySubject(1);
    this.validator.validate(token).subscribe((next) => {
      this.accessToken = next.data?.authenticate.token;
      if (this.accessToken) {
        const claims = jwt_decode<JwtPayload>(this.accessToken);
        this.payload = AuthService.toDto(claims);
      }
    });
    subject.next(true);
    return subject;
  }

  isAllowed(permissions: string[]) {
    return !permissions.some((p) => {
      return !this.payload?.permissions.includes(p);
    });
  }

  logout() {
    this.accessToken = undefined;
  }

  isAuthenticated(): boolean {
    return this.payload != null;
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
}
