import { AuthConfig } from 'angular-oauth2-oidc';

export const TOKEN_NAME = "access_token";

export const authConfig: AuthConfig = {
    issuer: 'http://localhost:8080',
    redirectUri: 'http://localhost:4200',
    loginUrl: 'http://localhost:8080/oauth/authorize',
    clientId: 'sampleClientId',
    scope: 'read write',    
    oidc: false
}