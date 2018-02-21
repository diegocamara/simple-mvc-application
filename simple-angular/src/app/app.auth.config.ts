import { AuthConfig } from 'angular-oauth2-oidc';

export const TOKEN_NAME = "access_token";

export const authConfig: AuthConfig = {
    issuer: 'http://localhost:8080',
    redirectUri: 'http://localhost:4200',
    loginUrl: 'http://localhost:8080/oauth/authorize',
    logoutUrl: 'http://localhost:8080/logout',    
    clientId: 'sampleClientId',    
    timeoutFactor: 5,
    scope: 'read write',    
    oidc: false,
    showDebugInformation: true
}