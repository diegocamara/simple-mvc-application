import { TOKEN_NAME } from './../app.auth.config';
import { OAuthService } from 'angular-oauth2-oidc';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { tokenNotExpired } from 'angular2-jwt';


@Injectable()
export class UserAuthGuard implements CanActivate {

    constructor(
        private oauthService: OAuthService,
        private router: Router
    ) {

    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

        if (tokenNotExpired(TOKEN_NAME, this.oauthService.getAccessToken())) {
            return true;
        } else {
            this.oauthService.logOut();
            this.router.navigate(['signin'], { queryParams: { redirectTo: state.url } });
            return false;
        }

    }

}