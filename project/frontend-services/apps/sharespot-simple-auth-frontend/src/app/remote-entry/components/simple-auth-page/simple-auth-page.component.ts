import {Component} from '@angular/core';
import {AuthService} from "@frontend-services/simple-auth-lib";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'frontend-services-simple-auth-page',
  templateUrl: './simple-auth-page.component.html',
  styleUrls: ['./simple-auth-page.component.scss']
})
export class SimpleAuthPageComponent {

  userName = "";
  secret = "";

  constructor(private _snackBar: MatSnackBar, private auth: AuthService) {
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, undefined, {
      duration: 3000
    });
  }

  hasValidCredentials() {
    return this.userName.length != 0 && this.secret.length != 0
  }

  login() {
    this.auth.login(this.userName, this.secret).subscribe(value => {
      value ? this.openSnackBar("Valid Credentials") : this.openSnackBar("Invalid Credentials")
    });
  }

  logout() {
    this.userName = "";
    this.secret = "";
    this.auth.logout();
  }

  isAuthenticated() {
    return this.auth.isAllowed();
  }
}
