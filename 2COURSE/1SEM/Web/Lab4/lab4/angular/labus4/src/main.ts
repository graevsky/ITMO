import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import {bootstrapApplication} from "@angular/platform-browser";
import {AppComponent} from "./app/app.component";
import {provideHttpClient} from "@angular/common/http";

bootstrapApplication(AppComponent, {
    providers: [provideHttpClient()]
})

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
