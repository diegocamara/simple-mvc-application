import { UserAuthGuard } from './guards/user-auth-guard.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';

import { OAuthModule, AuthConfig } from 'angular-oauth2-oidc';
import { AppComponent } from './app.component';
import { AppRoutingModule, routedComponents } from './app.routing.module';

@NgModule({
  declarations: [
    AppComponent,
    routedComponents
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    HttpClientModule,
    OAuthModule.forRoot()
  ],
  providers: [UserAuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
