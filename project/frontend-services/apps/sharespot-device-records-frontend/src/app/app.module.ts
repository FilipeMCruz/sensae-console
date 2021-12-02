import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {RouterModule} from '@angular/router';
import {RemoteEntryModule} from './remote-entry/entry.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {APOLLO_NAMED_OPTIONS} from 'apollo-angular';
import {ApolloClientOptions, InMemoryCache} from '@apollo/client/core';
import {environment} from '../environments/environment';
import {HttpLink} from 'apollo-angular/http';

export function createNamedApollo(httpLink: HttpLink): Record<string, ApolloClientOptions<any>> {
  return {
    deviceRecords: {
      name: 'deviceRecords',
      link: httpLink.create({
        uri: environment.backendURL.http
      }),
      cache: new InMemoryCache()
    }
  };
};

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    RemoteEntryModule,
    BrowserAnimationsModule,
    RouterModule.forRoot([], {initialNavigation: 'enabledBlocking'})
  ],
  providers: [
    {
      provide: APOLLO_NAMED_OPTIONS,
      useFactory: createNamedApollo,
      deps: [HttpLink],
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
