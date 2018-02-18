import { OAuthService } from 'angular-oauth2-oidc';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  constructor(
    private oauthService: OAuthService
  ) { }

  ngOnInit() {
  }

  login() {
    this.oauthService.initImplicitFlow();
  }

}
