import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "@frontend-services/simple-auth-lib";

@Injectable()
export class AuthGuardService implements CanActivate {

  constructor(private router: Router, private authService: AuthService) {

  }

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): boolean | UrlTree {

    if (!this.authService.isAllowed() && this.router.url !== "auth") {
      this.router.navigate(["auth"]);
      return false;
    }
    return true;
  }

  canShow(route: string) {
    return !(!this.authService.isAllowed() && route !== "auth");
  }
}
