import {Injectable} from "@angular/core";
import {ValidateCredentials} from "./services/ValidateCredentials";
import {ReplaySubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private outcome = false;

  constructor(private validator: ValidateCredentials) {
  }

  login(userName: string, secret: string) {
    const subject = new ReplaySubject(1);
    this.validator.validate({name: userName, secret: secret}).subscribe(
      next => {
        next.data ? this.outcome = next.data.credentials.valid : false;
        subject.next(this.outcome)
      }
    )
    return subject;
  }

  isAllowed() {
    return this.outcome;
  }

  logout() {
    this.outcome = false;
  }
}
