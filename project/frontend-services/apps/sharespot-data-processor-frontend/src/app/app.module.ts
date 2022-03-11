import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { RemoteEntryModule } from './remote-entry/entry.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { APOLLO_NAMED_OPTIONS, ApolloModule } from 'apollo-angular';
import { ApolloClientOptions, InMemoryCache } from '@apollo/client/core';
import { environment } from '../environments/environment';
import { HttpLink } from 'apollo-angular/http';
import { createLink } from '@frontend-services/mutual';

export function createNamedApollo(
  httpLink: HttpLink
): Record<string, ApolloClientOptions<any>> {
  return {
    dataProcessor: {
      link: createLink(httpLink, environment.endpoints.dataProcessor.backend),
      cache: new InMemoryCache(),
    },
  };
}

@NgModule({
  declarations: [AppComponent],
  imports: [
    ApolloModule,
    BrowserModule,
    RemoteEntryModule,
    BrowserAnimationsModule,
    RouterModule.forRoot([], { initialNavigation: 'enabledBlocking' }),
  ],
  providers: [
    {
      provide: APOLLO_NAMED_OPTIONS,
      useFactory: createNamedApollo,
      deps: [HttpLink],
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
