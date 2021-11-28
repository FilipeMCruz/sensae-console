import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {ROUTES} from './app.routes';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {APOLLO_OPTIONS} from "apollo-angular";
import {InMemoryCache} from "@apollo/client/core";
import {HttpLink} from "apollo-angular/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {environment} from "../environments/environment";
import {HomeComponent} from './components/home/home.component';
import {RecordsModule} from "./modules/records/records.module";
import {RouterModule} from "@angular/router";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(ROUTES),
    HttpClientModule,
    RecordsModule
  ],
  providers: [
    {
      provide: APOLLO_OPTIONS,
      useFactory: (httpLink: HttpLink) => {
        return {
          cache: new InMemoryCache(),
          link: httpLink.create({
            uri: environment.backendURL.http,
          }),
        };
      },
      deps: [HttpLink],
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
