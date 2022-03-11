import { HttpLink } from 'apollo-angular/http';
import { WebSocketLink } from '@apollo/client/link/ws';
import { split } from '@apollo/client/core';
import { getMainDefinition } from '@apollo/client/utilities';

export interface backendUriDetails {
  domain: string;
  http: uriDetails;
  ws?: uriDetails;
}

export interface uriDetails {
  scheme: string;
  path: string;
}

export function createLink(httpLink: HttpLink, details: backendUriDetails) {
  if (details.ws) {
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

  // Create a WebSocket link:
  const ws = new WebSocketLink({
    uri: buildWebSocketBackendUri(details),
    options: {
      reconnect: true,
      timeout: 30000,
    },
  });

  // using the ability to split links, you can send data to each link
  // depending on what kind of operation is being sent
  return split(
    // split based on operation type
    ({ query }) => {
      // @ts-ignore
      const { kind, operation } = getMainDefinition(query);
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
  return details.ws?.scheme + details.domain + details.ws?.path;
}
