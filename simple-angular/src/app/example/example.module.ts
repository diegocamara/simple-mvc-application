import { NgModule } from '@angular/core';
import { ExampleRoutingModule, routedComponents } from './example.routing.module';
import { ExampleService } from './example.service';

@NgModule({
    imports: [
        ExampleRoutingModule
    ],
    exports: [],
    declarations: [
        routedComponents
    ],
    providers: [
        ExampleService
    ],
})
export class ExampleModule { }
