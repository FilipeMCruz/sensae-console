import {Injectable} from "@angular/core";
import {ValidateCredentials} from "./services/ValidateCredentials";
import {ReplaySubject} from "rxjs";
import {UserIdentity} from "./model/UserIdentity";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private id?: UserIdentity;

  private accessToken?: string;

  constructor(private validator: ValidateCredentials) {
  }

  login(token: string) {
    const subject = new ReplaySubject(1);
    this.validator.validate(token).subscribe(next => {
      this.accessToken = next.data?.authenticate.token
    });
    subject.next(true);
    return subject;
  }

  isAllowed() {
    return this.id !== undefined;
  }

  logout() {
    this.accessToken = undefined;
    this.id = undefined;
  }
}
