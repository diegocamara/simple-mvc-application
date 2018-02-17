import { UserAuthGuard } from './guards/user-auth-guard.service';
import { HomeComponent } from './home/home.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
    {
        path: 'home',
        canActivate: [UserAuthGuard],
        component: HomeComponent
    },
    {
        path: 'example',
        loadChildren: 'app/example/example.module#ExampleModule'
    },
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    },
    {
        path: '**',
        component: PageNotFoundComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes, { useHash: true, enableTracing: false })],
    exports: [RouterModule]
})
export class AppRoutingModule {
}

export const routedComponents = [HomeComponent, PageNotFoundComponent];