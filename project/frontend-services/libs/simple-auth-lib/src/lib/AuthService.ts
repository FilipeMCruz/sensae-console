import {Injectable} from "@angular/core";
import {ValidateCredentials} from "./services/ValidateCredentials";
import {ReplaySubject} from "rxjs";
import {UserIdentity} from "./model/UserIdentity";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private id?: UserIdentity;

  constructor(private validator: ValidateCredentials) {
  }

  login(userName: string, email: string, oid: string) {
    const subject = new ReplaySubject(1);
    //TODO: request access token to internal auth server;
    this.id = new UserIdentity(userName, email, oid);
    subject.next(true);
    return subject;
  }

  isAllowed() {
    return this.id !== undefined;
  }

  logout() {
    this.id = undefined;
  }
}
