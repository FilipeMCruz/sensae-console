import {HttpLink} from 'apollo-angular/http';
import {split} from '@apollo/client/core';
import {getMainDefinition} from '@apollo/client/utilities';
import {GraphQLWsLink} from "@apollo/client/link/subscriptions";
import {createClient} from "graphql-ws";

export interface backendUriDetails {
  domain: string;
  http: uriDetails;
  websocket?: uriDetails;
}

export interface uriDetails {
  scheme: string;
  path: string;
}

export function createLink(httpLink: HttpLink, details: backendUriDetails) {
  if (details.websocket) {
    return createLinkWithWebsocket(httpLink, details);
  } else {
    return createSimpleLink(httpLink, details);
  }
}

function createSimpleLink(httpLink: HttpLink, details: backendUriDetails) {
  return httpLink.create({
    uri: buildHttpBackendUri(details),
  });
}

function createLinkWithWebsocket(
  httpLink: HttpLink,
  details: backendUriDetails
) {
  const http = httpLink.create({
    uri: buildHttpBackendUri(details),
  });

  const ws = new GraphQLWsLink(createClient({
    url: buildWebSocketBackendUri(details),
  }));

  // using the ability to split links, you can send data to each link
  // depending on what kind of operation is being sent
  return split(
    // split based on operation type
    ({query}) => {
      // @ts-ignore
      const {kind, operation} = getMainDefinition(query);
      return kind === 'OperationDefinition' && operation === 'subscription';
    },
    ws,
    http
  );
}

function buildHttpBackendUri(details: backendUriDetails): string {
  return details.http.scheme + details.domain + details.http.path;
}

function buildWebSocketBackendUri(details: backendUriDetails): string {
  return details.websocket?.scheme + details.domain + details.websocket?.path;
}
