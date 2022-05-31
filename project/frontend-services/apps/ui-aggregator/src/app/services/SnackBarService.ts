import {Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  constructor(
    private _snackBar: MatSnackBar) {
  }

  default(message: string) {
    return this._snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 3000,
    });
  }

  critical(message: string) {
    return this._snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 7000,
      panelClass: ['critical-snackbar']
    });
  }

  warning(message: string) {
    return this._snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 6000,
      panelClass: ['warning-snackbar']
    });
  }

  advisory(message: string) {
    return this._snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 5000,
      panelClass: ['advisory-snackbar']
    });
  }

  watch(message: string) {
    return this._snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 4000,
      panelClass: ['watch-snackbar']
    });
  }

  information(message: string) {
    return this._snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 3000,
      panelClass: ['information-snackbar']
    });
  }
}
