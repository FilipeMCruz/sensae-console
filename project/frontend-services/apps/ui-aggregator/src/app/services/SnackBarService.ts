import {Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from "@angular/router";
import {MatSnackBarConfig} from "@angular/material/snack-bar/snack-bar-config";

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  constructor(
    private _snackBar: MatSnackBar,
    private router: Router) {
  }

  default(message: string) {
    return this._snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 3000,
    });
  }

  error(message: string) {
    return this._snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 10000,
      panelClass: ['critical-snackbar']
    });
  }

  critical(message: string) {
    return this.notification(message, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      panelClass: ['critical-snackbar']
    });
  }

  warning(message: string) {
    return this.notification(message, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 6000,
      panelClass: ['warning-snackbar']
    });
  }

  advisory(message: string) {
    return this.notification(message, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 5000,
      panelClass: ['advisory-snackbar']
    });
  }

  watch(message: string) {
    return this.notification(message, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 4000,
      panelClass: ['watch-snackbar']
    });
  }

  information(message: string) {
    return this.notification(message, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 3000,
      panelClass: ['information-snackbar']
    });
  }

  private notification(message: string, config: MatSnackBarConfig) {
    const snackBar = this._snackBar
      .open(message, 'view', config);

    snackBar.onAction()
      .subscribe(() => this.router.navigate(['notification-management']))

    return snackBar;
  }
}
