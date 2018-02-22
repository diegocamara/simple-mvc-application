import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { JwksValidationHandler } from 'angular-oauth2-oidc';
import { authConfig } from './app.auth.config';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(
    private oauthService: OAuthService,
    private router: Router    
  ) {
    this.configureOAuth();
  }

  private configureOAuth() {
    this.oauthService.configure(authConfig);
    this.oauthService.tokenValidationHandler = new JwksValidationHandler();
    this.oauthService.tryLogin();
    this.oauthService.setupAutomaticSilentRefresh();
  }

  logout() {
    this.oauthService.logOut();
    this.router.navigate(['/signin']);
  }

  isLoggedIn() {
    return this.oauthService.getAccessToken() !== null;
  }

}
