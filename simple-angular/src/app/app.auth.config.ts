import { AuthConfig } from 'angular-oauth2-oidc';

export const TOKEN_NAME = "access_token";

export const authConfig: AuthConfig = {
    issuer: 'http://localhost:8080',
    redirectUri: 'http://localhost:4200',
    loginUrl: 'http://localhost:8080/oauth/authorize',
    logoutUrl: 'http://localhost:8080/logout',
    clientId: 'sampleClientId',
    scope: 'read write',
    oidc: false,
    showDebugInformation: true,
    // silent refresh config
    silentRefreshRedirectUri: window.location.origin + '/silent-refresh1.html',
    timeoutFactor: 0.5, // between 0 and 1 - silent refresh event is fired when token lifetime is over 50%.
    silentRefreshTimeout: 30.000, // milliseconds
}