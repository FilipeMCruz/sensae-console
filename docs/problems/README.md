# Problems

Current version:

- `system` : `0.6.0`

## Auth

The graphql subscription spec, and the used libraries, don't easily handle headers.
Links:

- https://github.com/apollographql/subscriptions-transport-ws/issues/171
- https://github.com/Netflix/dgs-framework/issues/450

The JWT token is, for now, being sent as a query input in subscriptions.

## Decoder

The JavaScript engine being used is Graaljs.
This engine is currently the state of the art of javascript engines running inside java.

It integrates natively with GraalVM, a new JDK. GraalVM + Spring Boot is still an experimental project and as such its adoption was abandoned. The current application is using stock java to run the app and therefore has lower performance. Still, this performance hit wasn't noticeable in the performed tests.

In the future one could try Quarkus, Micronaut or Spring Native as the backend framework to tackle this performance issue, since they work with GraalVM, and benefit from native images provided by GraalVM.
